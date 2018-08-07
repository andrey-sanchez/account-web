package com.bankito.account.data;

import java.util.Optional;

import com.bankito.account.entity.Transaction;

public interface TransactionRepository {
  Transaction newTx(Transaction tx);
  Optional<Transaction> getById(String id);
}
