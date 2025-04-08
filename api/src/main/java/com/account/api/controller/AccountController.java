package com.account.api.controller;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.account.api.application.AccountApplicationService;
import com.account.api.domain.model.Account;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class AccountController {
    
    @Autowired
    private AccountApplicationService accountApplicationService;

    @GetMapping("/accounts")
    public ResponseEntity<Set<Account>> getAccounts() {
        System.out.println("Requested resource GET /accounts");
        Set<Account> result = accountApplicationService.getAll();
        return new ResponseEntity<Set<Account>>(result, HttpStatus.OK);
    }

    @GetMapping("/balance")
    public ResponseEntity<Double> getBalance(@RequestParam String account_id) {
        System.out.println("Requested resource GET /balance: " + account_id);
        Double result = accountApplicationService.getAccountBalance(account_id);
        return new ResponseEntity<Double>(result, HttpStatus.OK);
    }
}
