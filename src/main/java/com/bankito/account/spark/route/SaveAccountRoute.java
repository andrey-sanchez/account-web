package com.bankito.account.spark.route;

import com.bankito.account.data.AccountRepository;
import com.bankito.account.entity.Account;
import com.bankito.account.spark.WebApp;

import org.eclipse.jetty.http.HttpStatus;
import org.eclipse.jetty.http.MimeTypes;

import spark.Request;
import spark.Response;
import spark.Route;

public class SaveAccountRoute implements Route {

  private final AccountRepository accounts;
  
  public SaveAccountRoute(AccountRepository accounts) {
    this.accounts = accounts;
  }

	public Object handle(final Request request, final Response response) throws Exception {
    final String id = request.params("id");
    Account account = WebApp.gson.fromJson(request.body(), Account.class);
    account.setId(id);
    accounts.save(account);
    response.status(HttpStatus.OK_200);
    response.type(MimeTypes.Type.APPLICATION_JSON_UTF_8.toString());
    return account;
  }

}
