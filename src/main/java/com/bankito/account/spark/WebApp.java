package com.bankito.account.spark;

import com.bankito.account.data.jdbi.JdbiAccountRepository;
import com.bankito.account.data.jdbi.JdbiMovementRepository;
import com.bankito.account.data.jdbi.JdbiTransactionRepository;

import com.bankito.account.data.AccountRepository;
import com.bankito.account.data.MovementRepository;
import com.bankito.account.data.TransactionRepository;
import com.bankito.account.spark.route.GetAccountRoute;
import com.bankito.account.spark.route.GetCurrentBalanceRoute;
import com.bankito.account.spark.route.GetMovementByAccountIdRoute;
import com.bankito.account.spark.route.GetMovementByIdRoute;
import com.bankito.account.spark.route.GetTransactionRoute;
import com.bankito.account.spark.route.NewTransactionRoute;
import com.bankito.account.spark.route.RecreateDbRoute;
import com.bankito.account.spark.route.SaveAccountRoute;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.core.h2.H2DatabasePlugin;

import spark.Spark;
import spark.servlet.SparkApplication;

public class WebApp implements SparkApplication {

  public static final Gson gson = new Gson();
  public static final String url = "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1";
  
	@Override
	public void init() {
    Jdbi jdbi = Jdbi.create(url);
    jdbi.installPlugin(new H2DatabasePlugin());

    final AccountRepository accounts = makeAccounts(jdbi);
    final TransactionRepository transactions = makeTransactions(jdbi);
    final MovementRepository movements = makeMovements(jdbi);
    
    final GetAccountRoute getAccountRoute = new GetAccountRoute(accounts);
    final GetTransactionRoute getTransactionRoute = new GetTransactionRoute(transactions);
    final SaveAccountRoute saveAccountRoute = new SaveAccountRoute(accounts);
    final NewTransactionRoute newTransactionRoute = new NewTransactionRoute(transactions);
    final GetCurrentBalanceRoute getCurrentBalance = new GetCurrentBalanceRoute();
    final GetMovementByAccountIdRoute getMovementsByAccountIdRoute = new GetMovementByAccountIdRoute(movements);
    final GetMovementByIdRoute getMovementRoute = new GetMovementByIdRoute(movements);
    final RecreateDbRoute recreateDbRoute = new RecreateDbRoute(jdbi);

    Spark.path("/account", () -> {
      Spark.put("/:id", saveAccountRoute);
      Spark.get("/:id", getAccountRoute);
      Spark.get("/:id/currentbalance", getCurrentBalance);
      Spark.get("/:id/movement", getMovementsByAccountIdRoute);
    });
    Spark.path("/transaction", () -> {
      Spark.get("/:id", getTransactionRoute);
      Spark.post("", newTransactionRoute);
    });

    Spark.path("/movement", () -> {
      Spark.get("/:id", getMovementRoute);
    });

    Spark.post("/recreatedb", recreateDbRoute);
	}

  public static AccountRepository makeAccounts(Jdbi jdbi) {
    return new JdbiAccountRepository(jdbi);
  }
  public static TransactionRepository makeTransactions(Jdbi jdbi) {
    return new JdbiTransactionRepository(jdbi);
  }
  public static MovementRepository makeMovements(Jdbi jdbi) {
    return new JdbiMovementRepository(jdbi);
  }

}
