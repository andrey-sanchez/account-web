package com.bankito.account.data.jdbi;

import com.bankito.account.data.MovementRepository;
import com.bankito.account.entity.Movement;
import com.bankito.account.entity.MovementList;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.core.statement.Query;

import java.time.Instant;
import java.util.Optional;

public class JdbiMovementRepository implements MovementRepository {

  private final Jdbi jdbi;

  public JdbiMovementRepository(Jdbi jdbi) {
    this.jdbi = jdbi;
  }

	public Optional<Movement> getById(String id) {
    return jdbi.withHandle(handle -> {
      return
        handle.select("SELECT * FROM movement WHERE id = ?", id)
          .mapToBean(Movement.class)
          .findFirst();
    });
	}

	public MovementList getByAccount(String accountId, Optional<Instant> startInclusive, Optional<Instant> endExclusive) {
    return jdbi.withHandle(handle -> {
      final MovementList result =  new MovementList();
      final StringBuilder builder = new StringBuilder();
      builder.append("SELECT * FROM movement WHERE account_id = :account_id");
      if (startInclusive.isPresent()) {
        builder.append(" AND date >= :start");
      }

      if (endExclusive.isPresent()) {
        builder.append(" AND date < :end");
      }

      Query query = handle.createQuery(builder.toString());
      query = query.bind("account_id", accountId);
      if (startInclusive.isPresent()) {
        query = query.bind("start", startInclusive.get());
      }

      if (startInclusive.isPresent()) {
        query = query.bind("end", endExclusive.get());
      }

      result.addAll(query.mapToBean(Movement.class).list());
      return result;
    });
	}
}
