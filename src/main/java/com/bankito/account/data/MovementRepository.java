package com.bankito.account.data;

import java.time.Instant;
import java.util.Optional;

import com.bankito.account.entity.Movement;
import com.bankito.account.entity.MovementList;

public interface MovementRepository {
  Optional<Movement> getById(String id);
  MovementList getByAccount(String accountId, Optional<Instant> startInclusive, Optional<Instant> endExclusive);
}
