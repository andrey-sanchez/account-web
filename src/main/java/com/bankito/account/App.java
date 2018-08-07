package com.bankito.account;

import com.bankito.account.spark.WebApp;

import spark.Spark;

/**
 * Hello world!
 *
 */
public class App {

  public static void main(String[] args) {
    final WebApp app = new WebApp();

    Spark.port(8080);
    app.init();
    Spark.awaitInitialization();

    System.out.println("Listening on http://localhost:8080/");
  }
}
