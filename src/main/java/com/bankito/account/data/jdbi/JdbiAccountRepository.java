package com.bankito.account.data.jdbi;

import java.util.Optional;

import com.bankito.account.data.AccountRepository;
import com.bankito.account.entity.Account;

import org.jdbi.v3.core.Jdbi;

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
        handle.createUpdate("UPDATE account SET owner = :owner WHERE id = :id").bindBean(account);
      }
      else {
        handle.createUpdate("INSERT INTO account (id, owner) VALUES (:id, :owner)").bindBean(account);
      }
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
