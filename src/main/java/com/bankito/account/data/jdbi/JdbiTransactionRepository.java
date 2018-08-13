package com.bankito.account.data.jdbi;

import com.bankito.account.data.TransactionRepository;
import com.bankito.account.entity.Account;
import com.bankito.account.entity.Movement;
import com.bankito.account.entity.MovementList;
import com.bankito.account.entity.Transaction;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.core.transaction.TransactionIsolationLevel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Instant;
import java.util.Optional;

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
                .execute("INSERT INTO transaction (id, timestamp, type, owner_account_id) VALUES (?, ?, ?, ?)", tx.getId(), tx.getTimestamp(), "deposit", "01");

              log.info("saving movements");
              // TODO: escribir aqui movimientos
              for (Movement m : tx.getMovements()) {
                m.setTransaction_id(tx.getId());
                m.setTimestamp(now.getEpochSecond());

                //update balance and check if is negative
                int count = tHandle
                  .execute("UPDATE account SET balance = balance + ? WHERE id = ?", m.getAmount(), m.getAccount_id());
                if (count < 1) {
                  throw new RuntimeException("Account not found");
                }

                Optional<Account> account =
                  tHandle.select("SELECT * FROM account WHERE id = ?", m.getAccount_id())
                    .mapToBean(Account.class)
                    .findFirst();
                if (!account.isPresent() || account.get().getBalance() < 0) {
                  throw new RuntimeException("Insufficient Balance");
                }

                tHandle
                  .createUpdate("INSERT INTO movement (id, account_id, transaction_id, timestamp, " +
                    "amount, description) VALUES (:id, :account_id, :transaction_id, :timestamp, :amount, :description)")
                  .bindBean(m)
                  .execute();
              }

              return tx;
            });
      } catch (Exception ex) {
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
      } else {
        log.info("transaction id:" + id + " not found");
      }

      return result;
    });
  }

}
