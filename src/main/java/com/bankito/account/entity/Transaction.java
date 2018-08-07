package com.bankito.account.entity;

import java.time.Instant;

public class Transaction {

  private String id;
  private Instant timestamp;
  private TransactionType type;
  private String ownerAccountId;
  private MovementList movementList;

  public String getId() { return this.id; }
  public Instant getTimestamp() { return this.timestamp; }
  public TransactionType getType() { return this.type; }
  public String getOwnerAccountId() { return this.ownerAccountId;}
  public MovementList getMovements() { return this.movementList; }

  public void setId(String value) { this.id = value; }
  public void setTimestamp(Instant value) { this.timestamp = value; }
  public void setType(TransactionType value) { this.type = value; }
  public void setOwnerAccountId(String value) { this.ownerAccountId = value; }
  public void setMovements(MovementList value) { this.movementList = value; }

}
