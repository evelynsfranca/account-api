package com.account.api.repository;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Repository;

import com.account.api.domain.model.Account;

@Repository
public class AccountRepositoryImpl implements AccountRepository {

    private Set<Account> accounts = new HashSet<>();

    public AccountRepositoryImpl() {
        accounts.add(new Account("300", 0.0, new ArrayList<>()));
    }

    @Override
    public Account findById(String id) {
        return accounts.stream()
                .filter(acc -> acc.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public void save(Account account) {
        accounts.add(account);
    }

    @Override
    public Set<Account> findAll() {
        return accounts;
    }

    @Override
    public void reset() {
        accounts = new HashSet<>();
    }
}
