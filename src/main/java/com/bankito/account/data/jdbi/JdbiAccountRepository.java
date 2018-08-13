package com.bankito.account.data.jdbi;

import com.bankito.account.data.AccountRepository;
import com.bankito.account.entity.Account;
import org.jdbi.v3.core.Jdbi;

import java.util.Optional;

public class JdbiAccountRepository implements AccountRepository {

  private final Jdbi jdbi;

  public JdbiAccountRepository(Jdbi jdbi) {
    this.jdbi = jdbi;
  }

	public void save(Account account) {
    jdbi.useHandle(handle -> {
      Optional<Account> acc = 
        handle.select("SELECT * FROM account WHERE id = ?", account.getId())
          .mapToBean(Account.class)
          .findFirst();
      if (acc.isPresent()) {
        handle.createUpdate("UPDATE account SET owner = :owner, balance = :balance WHERE id = :id")
          .bindBean(account)
          .execute();
      }
      else {
        handle.createUpdate("INSERT INTO account (id, owner, balance) VALUES (:id, :owner, :balance)")
          .bindBean(account)
          .execute();
      }

      handle.close();
    });
	}

	public Optional<Account> getById(String id) {
    return jdbi.withHandle(handle -> {
      return
        handle.select("SELECT * FROM account WHERE id = ?", id)
          .mapToBean(Account.class)
          .findFirst();
    });
	}

}
