package com.bankito.account.spark.route;

import org.eclipse.jetty.http.HttpStatus;
import org.eclipse.jetty.http.MimeTypes;
import org.jdbi.v3.core.Jdbi;

import spark.Request;
import spark.Response;
import spark.Route;

public class RecreateDbRoute implements Route {

  private final Jdbi jdbi;

  public RecreateDbRoute(final Jdbi jdbi) {
    this.jdbi = jdbi;
  }

	@Override
	public Object handle(Request request, Response response) throws Exception {
    jdbi.useHandle(handler -> {
      handler.execute("DROP TABLE IF EXISTS transaction");
      handler.execute("DROP TABLE IF EXISTS movement");
      handler.execute("DROP TABLE IF EXISTS account");
      handler.execute("CREATE TABLE account (id NVARCHAR(32) NOT NULL, owner NVARCHAR(64) NOT NULL)");
      handler.execute("CREATE TABLE movement (id NVARCHAR(32) NOT NULL, account_id NVARCHAR(32) NOT NULL, transaction_id NVARCHAR(32) NOT NULL, amount BIGINT NOT NULL, timestamp BIGINT NOT NULL, description NVARCHAR(128) NOT NULL)");
      handler.execute("CREATE TABLE transaction (id NVARCHAR(32) NOT NULL, owner NVARCHAR(64) NOT NULL, timestamp bigint NOT NULL, type VARCHAR(16) NOT NULL)");
      handler.execute("SELECT * FROM account WHERE id = ''");
      handler.execute("SELECT * FROM movement WHERE id = ''");
      handler.execute("SELECT * FROM transaction WHERE id = ''");

    });

    response.status(HttpStatus.OK_200);
    response.type(MimeTypes.Type.TEXT_PLAIN_UTF_8.toString());
    return "";
	}

}
