package com.bankito.account.entity;

import java.time.Instant;

public class Movement {
  private String id;
  private String accountId;
  private String transactionId;
  private Instant timestamp;
  private long amount;
  private String description;

  public String getId() { return this.id; }
  public String getAccountId() { return this.accountId; }
  public String getTransactionId() { return this.transactionId; }
  public Instant getTimestamp() { return this.timestamp; }
  public long getAmount() { return this.amount; }
  public String getDescription() { return this.description; }

  public void setId(String value) { this.id = value; }
  public void setAccountId(String value) { this.accountId = value; }
  public void setTransactionId(String value) { this.transactionId = value; }
  public void setTimestamp(Instant value) { this.timestamp = value; }
  public void setAmount(long value) { this.amount = value; }
  public void setDescription(String value) { this.description = value; }

}
