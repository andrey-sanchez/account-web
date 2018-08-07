package com.bankito.account.entity;

import java.util.List;

public class Transaction {

  private String id;
  private long timestamp;
  private TransactionType type;
  private String owner_account_id;
  private List<Movement> movements;

  public String getId() { return this.id; }
  public long getTimestamp() { return this.timestamp; }
  public TransactionType getType() { return this.type; }
  public String getOwnerAccountId() { return this.owner_account_id;}
  public List<Movement> getMovements() { return this.movements; }

  public void setId(String value) { this.id = value; }
  public void setTimestamp(long value) { this.timestamp = value; }
  public void setType(TransactionType value) { this.type = value; }
  public void setOwnerAccountId(String value) { this.owner_account_id = value; }
  public void setMovements(List<Movement> value) { this.movements = value; }

}
