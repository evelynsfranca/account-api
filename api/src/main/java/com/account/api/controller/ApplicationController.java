package com.account.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.account.api.application.AccountApplicationService;
import com.account.api.application.EventApplicationService;

@RestController
public class ApplicationController {

    @Autowired
    private AccountApplicationService accountApplicationService;

    @Autowired 
    private EventApplicationService eventApplicationService;
    
    @PostMapping("/reset")
    public ResponseEntity<String> reset() {
        
        System.out.println("Requested resource POST /reset");
        accountApplicationService.reset();
        eventApplicationService.reset();

        return new ResponseEntity<String>(HttpStatus.OK);
    }
}
