package com.account.api.application;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.account.api.domain.model.Account;
import com.account.api.domain.model.Event;
import com.account.api.domain.service.AccountService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AccountApplicationService {
    
    @Autowired
    private AccountService accountService;

    public Set<Account> getAll() {
        return accountService.getAll();
    }

    public Account getAccount(String accountId) {
        Account account = accountService.getAccount(accountId);

        if (account == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "0"); 
        }

        return account;
    }
    
    public Account updateAccount(Account account, Event event) {
        Account entity = accountService.getAccount(account.getId());     
        return accountService.updateAccount(entity, event);
    }

    public void reset() {
        accountService.reset();
    }
}
