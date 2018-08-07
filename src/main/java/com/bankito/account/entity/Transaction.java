package com.bankito.account.entity;

import java.time.Instant;

public class Transaction {

  private String id;
  private Instant timestamp;
  private TransactionType type;
  private String owner;
  private MovementList movementList;

  public String getId() { return this.id; }
  public Instant getTimestamp() { return this.timestamp; }
  public TransactionType getType() { return this.type; }
  public String getOwner() { return this.owner;}
  public MovementList getMovements() { return this.movementList; }

  public void setId(String value) { this.id = value; }
  public void setTimestamp(Instant value) { this.timestamp = value; }
  public void setType(TransactionType value) { this.type = value; }
  public void setOwner(String value) { this.owner = value; }
  public void setMovements(MovementList value) { this.movementList = value; }

}
