package com.bankito.account.entity;

public class AccountBalance {
  private final String account;
  private final long balance;

  public AccountBalance(String account, long balance) {
    this.account = account;
    this.balance = balance;
  }

  public String getAccount() {
    return account;
  }

  public long getBalance() {
    return balance;
  }
}
