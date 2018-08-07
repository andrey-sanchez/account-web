package com.bankito.account.data.jdbi;

import java.time.Instant;
import java.util.Optional;

import com.bankito.account.data.TransactionRepository;
import com.bankito.account.entity.Movement;
import com.bankito.account.entity.MovementList;
import com.bankito.account.entity.Transaction;

import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.core.transaction.TransactionIsolationLevel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JdbiTransactionRepository implements TransactionRepository {

  private static final Logger log = LoggerFactory.getLogger(JdbiTransactionRepository.class);
  private final Jdbi jdbi;

  public JdbiTransactionRepository(Jdbi jdbi) {
    this.jdbi = jdbi;
  }

  public Transaction newTx(Transaction tx) {

    final Instant now = Instant.now();
    return jdbi.withHandle(handle -> {
      try {
        tx.setTimestamp(now.getEpochSecond());
        return
        handle.inTransaction(
          TransactionIsolationLevel.SERIALIZABLE, 
          tHandle -> {
            log.info("saving transaction");
            tHandle
              .createQuery("INSERT INTO transaction (id, timestamp, type, owner_account_id) VALUES (:id, :timestamp, :type, :owner_account_id)")
              .bindBean(tx);

            log.info("saving movements");
            for(Movement m : tx.getMovements()) {
              m.setTransactionId(tx.getId());
              m.setTimestamp(now.getEpochSecond());
              tHandle
                .createQuery("INSERT INTO movement (id, account_id, transaction_id, timestamp, value, description) VALUES (:id, :account_id, :transaction_id, :timestamp, :value, :description)")
                .bindBean(m);            
            }
            
            return tx; 
          });
      } catch(Exception ex) {
        throw new RuntimeException(ex);
      }
    });
  }
  
	public Optional<Transaction> getById(String id) {
    return jdbi.withHandle(handle -> {
        Optional<Transaction> result =
        handle.select("SELECT * FROM transaction WHERE id = ?", id)
          .mapToBean(Transaction.class)
          .findFirst();
        if (result.isPresent()) {
          MovementList movements = new MovementList();
          handle.select("SELECT * FROM movement WHERE transaction_id = ?", id)
            .mapToBean(Movement.class)
            .iterator()
            .forEachRemaining(m -> movements.add(m));
          result.get().setMovements(movements);
        }
        
        return result;
    });
	}

}
