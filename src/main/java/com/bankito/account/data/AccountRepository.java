package com.bankito.account.data;

import java.util.Optional;

import com.bankito.account.entity.Account;

public interface AccountRepository {
  void save(Account account);
  Optional<Account> getById(String id);
}
