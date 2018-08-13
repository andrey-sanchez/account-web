package com.bankito.account.entity;

public class Movement {
  private String id;
  private String account_id;
  private String transaction_id;
  private long timestamp;
  private long amount;
  private String description;

  public String getId() { return this.id; }
  public String getAccount_id() { return this.account_id; }
  public String getTransaction_id() { return this.transaction_id; }
  public long getTimestamp() { return this.timestamp; }
  public long getAmount() { return this.amount; }
  public String getDescription() { return this.description; }

  public void setId(String value) { this.id = value; }
  public void setAccount_id(String value) { this.account_id = value; }
  public void setTransaction_id(String value) { this.transaction_id = value; }
  public void setTimestamp(long value) { this.timestamp = value; }
  public void setAmount(long value) { this.amount = value; }
  public void setDescription(String value) { this.description = value; }

}
