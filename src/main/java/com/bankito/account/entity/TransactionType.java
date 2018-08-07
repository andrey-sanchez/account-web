package com.bankito.account.entity;

import com.google.gson.annotations.SerializedName;

public enum TransactionType {

  @SerializedName("status")
  DEPOSIT(),
  @SerializedName("withdraw")
  WITHDRAW(),
  @SerializedName("transfer")
  TRANSFER()
}
