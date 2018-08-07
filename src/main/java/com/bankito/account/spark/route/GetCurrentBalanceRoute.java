package com.bankito.account.spark.route;

import org.eclipse.jetty.http.HttpStatus;
import org.eclipse.jetty.http.MimeTypes;

import spark.Request;
import spark.Response;
import spark.Route;

public class GetCurrentBalanceRoute implements Route {
  public GetCurrentBalanceRoute() {
  }

  @Override
  public Object handle(Request request, Response response) throws Exception {
    response.status(HttpStatus.NOT_IMPLEMENTED_501);
    response.type(MimeTypes.Type.TEXT_PLAIN_UTF_8.toString());
    return "";
  }
  
}
