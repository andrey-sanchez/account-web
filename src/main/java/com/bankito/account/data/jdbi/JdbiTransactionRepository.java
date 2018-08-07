package com.bankito.account.data.jdbi;

import java.util.Optional;

import com.bankito.account.data.TransactionRepository;
import com.bankito.account.entity.Movement;
import com.bankito.account.entity.MovementList;
import com.bankito.account.entity.Transaction;

import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.core.transaction.TransactionIsolationLevel;

public class JdbiTransactionRepository implements TransactionRepository {

  private final Jdbi jdbi;

  public JdbiTransactionRepository(Jdbi jdbi) {
    this.jdbi = jdbi;
  }

  public Transaction newTx(Transaction tx) {
    return jdbi.withHandle(handle -> {
      try {
        return
        handle.inTransaction(
          TransactionIsolationLevel.SERIALIZABLE, 
          tHandle -> { 
            tHandle
              .createQuery("INSERT INTO transaction (id, timestamp) VALUES (:id, :timestamp)")
              .bindBean(tx);

            for(Movement m : tx.getMovements()) {
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
