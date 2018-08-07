package com.bankito.account.spark.route;

import java.time.Instant;
import java.util.Optional;

import com.bankito.account.data.MovementRepository;
import com.bankito.account.entity.MovementList;
import com.bankito.account.spark.WebApp;

import org.eclipse.jetty.http.HttpStatus;
import org.eclipse.jetty.http.MimeTypes;

import spark.Request;
import spark.Response;
import spark.Route;

public class GetMovementByAccountIdRoute implements Route {

  private final MovementRepository movements;

  public GetMovementByAccountIdRoute(MovementRepository movements) {
    this.movements = movements;
  }

	@Override
	public Object handle(Request request, Response response) throws Exception {
    final String id = request.params("id");
    Optional<Instant> start = Optional.empty();
    Optional<Instant> end = Optional.empty();
    
    if (request.queryParams("start") != null) {
      start = Optional.of(Instant.parse(request.queryParams("start")));
    }

    if (request.queryParams("end") != null) {
      end = Optional.of(Instant.parse(request.queryParams("end")));
    }

    final MovementList movementList = movements.getByAccount(id, start, end);
    response.status(HttpStatus.OK_200);
    response.type(MimeTypes.Type.APPLICATION_JSON_UTF_8.toString());
    return WebApp.gson.toJson(movementList);
	}

}
