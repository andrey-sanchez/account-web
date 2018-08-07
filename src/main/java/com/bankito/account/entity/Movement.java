package com.bankito.account.entity;

import java.time.Instant;

public class Movement {
  private String id;
  private String accountId;
  private String transactionId;
  private Instant timestamp;
  private long amount;
  private String description;

  public String id() { return this.id; }
  public String accountId() { return this.accountId; }
  public String transactionId() { return this.transactionId; }
  public Instant timestamp() { return this.timestamp; }
  public long amount() { return this.amount; }
  public String description() { return this.description; }

  public void id(String value) { this.id = value; }
  public void accountId(String value) { this.accountId = value; }
  public void transactionId(String value) { this.transactionId = value; }
  public void timestamp(Instant value) { this.timestamp = value; }
  public void amount(long value) { this.amount = value; }
  public void description(String value) { this.description = value; }

}
