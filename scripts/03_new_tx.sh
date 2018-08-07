#!/usr/bin/env bash

OWN_ID=01
TX_ID=$(uuidgen)
MOV_ID=$(uuidgen)
TX_BODY=$(sed "s/{owner_account_id}/$OWN_ID/g" ./scripts/json/new_deposit_tx.json | sed "s/{mov_id}/$MOV_ID/g")

echo curl -v -X POST "http://localhost:8080/transaction/$TX_ID" -H "Content-Type: application/json" -d "$TX_BODY"
