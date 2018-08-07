package com.bankito.account.spark.route;

import java.util.Optional;

import com.bankito.account.data.AccountRepository;
import com.bankito.account.entity.Account;
import com.bankito.account.spark.WebApp;

import org.eclipse.jetty.http.HttpStatus;
import org.eclipse.jetty.http.MimeTypes;

import spark.Request;
import spark.Response;
import spark.Route;

public class GetAccountRoute implements Route {

  private final AccountRepository accounts;

  public GetAccountRoute(AccountRepository accounts) {
    this.accounts = accounts;
  }

  @Override
  public Object handle(Request request, Response response) throws Exception {
    final String id = request.params("id");
    final Optional<Account> account = accounts.getById(id);
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
