package com.bankito.account.spark.route;

import java.util.Optional;

import com.bankito.account.data.MovementRepository;
import com.bankito.account.entity.Movement;
import com.bankito.account.spark.WebApp;

import org.eclipse.jetty.http.HttpStatus;
import org.eclipse.jetty.http.MimeTypes;

import spark.Request;
import spark.Response;
import spark.Route;

public class GetMovementByIdRoute implements Route {
  private final MovementRepository movements;

  public GetMovementByIdRoute(MovementRepository movements) {
    this.movements = movements;
  }

  @Override
  public Object handle(Request request, Response response) throws Exception {
    final String id = request.params("id");
    final Optional<Movement> account = movements.getById(id);
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

