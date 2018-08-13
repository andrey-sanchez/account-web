#!/usr/bin/env bash

OWN_ID=01
TX_ID=$(uuidgen)
MOV_ID=$(uuidgen)
TX_BODY=$(sed "s/{owner_account_id}/$OWN_ID/g" ./scripts/json/new_withdraw_tx.json | sed "s/{mov_id}/$MOV_ID/g" | sed "s/{tx_id}/$TX_ID/g")

curl -v -X POST "http://localhost:8080/transaction" -H "Content-Type: application/json" -d "$TX_BODY"
