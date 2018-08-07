package com.bankito.account.spark.route;

import com.bankito.account.data.TransactionRepository;
import com.bankito.account.entity.Transaction;
import com.bankito.account.spark.WebApp;

import org.eclipse.jetty.http.HttpStatus;
import org.eclipse.jetty.http.MimeTypes;

import spark.Request;
import spark.Response;
import spark.Route;

public class NewTransactionRoute implements Route {

  private final TransactionRepository transactions;
  
  public NewTransactionRoute(TransactionRepository transactions) {
    this.transactions = transactions;
  }
  
  public Object handle(final Request request, final Response response) throws Exception {
    Transaction tx = WebApp.gson.fromJson(request.body(), Transaction.class);
    tx = transactions.newTx(tx);
    response.status(HttpStatus.CREATED_201);
    response.type(MimeTypes.Type.APPLICATION_JSON_UTF_8.toString());
    return WebApp.gson.toJson(tx);
	}
  
}
