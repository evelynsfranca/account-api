package com.account.api.domain.service;

import java.util.Random;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.account.api.domain.enumeration.EventType;
import com.account.api.domain.model.Account;
import com.account.api.domain.model.Event;
import com.account.api.repository.AccountRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    public Set<Account> getAll() {
        return accountRepository.findAll();
    }

    public Account getAccount(String accountId) {
        return accountRepository.findById(accountId);
    }

    public Account updateAccount(Account account, Event event) {

        Double originValue = 
            (event.getType().equals(EventType.transfer) || event.getType().equals(EventType.withdraw))
                && event.getOrigin().getId().equals(account.getId())
                    ? event.getAmount() * (-1)
                    : event.getAmount();

        Double newBalance = account.getBalance() + originValue;
        account.setBalance(newBalance);
        account.getEvents().add(event);
        return account;
    }

    public Account createAccountFromEvent(Event event) {

        Account account = new Account();
        Random rand = new Random();

        String id = 
            event.getDestination() == null 
                ? String.valueOf(rand.nextInt(999))
                : event.getDestination().getId();

        account.setId(id);
        account.setBalance(0.0);

        accountRepository.save(account);

        return account;
    }

    public void reset() {
        accountRepository.reset();
    }
}   
