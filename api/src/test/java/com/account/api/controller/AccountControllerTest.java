package com.account.api.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import com.account.api.domain.model.Account;
import com.account.api.repository.AccountRepository;

@SpringBootTest
@AutoConfigureMockMvc
class AccountControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AccountRepository accountRepository;

    @BeforeEach
    public void config() throws Exception {
        mockMvc.perform(post("/reset"))
                .andExpect(status().isOk());
    }
    
    @Test
    void testGetBalance() throws Exception {
        Account account = accountRepository.findById("300");

        mockMvc.perform(get("/balance")
                .param("account_id", "300"))
                .andExpect(status().isOk())
                .andExpect(content().string(account.getBalance().toString()));
    }

    @Test
    void testGetBalanceNotFound() throws Exception {
        mockMvc.perform(get("/balance")
                .param("account_id", "999"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("0"));
    }
}
