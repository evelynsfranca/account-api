package com.account.api.application;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.account.api.domain.model.Account;
import com.account.api.domain.model.Event;
import com.account.api.domain.service.AccountService;
import com.account.api.exception.BusinessException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AccountApplicationService {
    
    @Autowired
    private AccountService accountService;

    public Set<Account> getAll() {
        return accountService.getAll();
    }

    public Double getAccountBalance(String accountId) {
        Account account = accountService.getAccount(accountId);

        if (account == null) {
            throw new BusinessException(HttpStatus.NOT_FOUND, "0"); 
        }

        return account.getBalance();
    }
    
    public Account updateAccount(Account account, Event event) {
        Account entity = accountService.getAccount(account.getId());     
        return accountService.updateAccount(entity, event);
    }

    public void reset() {
        accountService.reset();
    }
}
