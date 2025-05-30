package com.account.api.repository;

import java.util.Set;

import com.account.api.domain.model.Account;

public interface AccountRepository {

    Account findById(String id);

    Account save(Account account);

    Set<Account> findAll();

    void reset();
  
} 