package com.bankito.account.entity;

public class Account {
  private String id;
  private String owner;
  private long balance;

  public String getId() { return this.id; }
  public String getOwner() { return this.owner; }

  public void setId(String value) { this.id = value; }
  public void setOwner(String value) { this.owner = value; }

  public long getBalance() {
    return balance;
  }

  public void setBalance(long balance) {
    this.balance = balance;
  }
}
