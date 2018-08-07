package com.bankito.account.spark.route;

import java.util.Optional;

import com.bankito.account.data.TransactionRepository;
import com.bankito.account.entity.Transaction;
import com.bankito.account.spark.WebApp;

import org.eclipse.jetty.http.HttpStatus;
import org.eclipse.jetty.http.MimeTypes;

import spark.Request;
import spark.Response;
import spark.Route;

public class GetTransactionRoute implements Route {

  private final TransactionRepository transactions;
  
  public GetTransactionRoute(TransactionRepository transactions) {
    this.transactions = transactions;
  }
	@Override
	public Object handle(Request request, Response response) throws Exception {
    final String id = request.params("id");
    final Optional<Transaction> account = transactions.getById(id);
    if (!account.isPresent()) {
      response.status(HttpStatus.NOT_FOUND_404);
      response.type(MimeTypes.Type.TEXT_PLAIN_UTF_8.toString());
      return "";
    }
    else {
      response.status(HttpStatus.OK_200);
      response.type(MimeTypes.Type.APPLICATION_JSON_UTF_8.toString());
      return WebApp.gson.toJson(account.get());
    }
	}

}
