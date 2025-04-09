package com.account.api.repository;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Repository;

import com.account.api.domain.model.Account;

@Repository
public class AccountRepositoryImpl implements AccountRepository {

    private Account account = new Account("300", 0.0, new ArrayList<>());
    private Set<Account> accounts = new HashSet<>();

    public AccountRepositoryImpl() {
        accounts.add(account);
    }

    @Override
    public Account findById(String id) {
        return accounts.stream()
                .filter(acc -> acc.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public Account save(Account account) {
        accounts.add(account);
        return account;
    }

    @Override
    public Set<Account> findAll() {
        return accounts;
    }

    @Override
    public void reset() {
        this.accounts.clear();        
        Account defaultAccount = new Account("300", 0.0, new ArrayList<>());
        accounts = new HashSet<>(Set.of(defaultAccount));
    }
}
