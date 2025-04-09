package com.account.api.controller;

import com.account.api.domain.enumeration.EventType;
import com.account.api.domain.model.Account;
import com.account.api.dto.event.EventToCreateDTO;
import com.account.api.repository.AccountRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;

import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
class EventControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;
    
    @Autowired
    private AccountRepository accountRepository;

    @BeforeEach
    public void config() throws Exception {
        mockMvc.perform(post("/reset"))
                .andExpect(status().isOk());
    }

    @Test
    void testCreateDepositEvent() throws Exception {
        EventToCreateDTO dto = new EventToCreateDTO();
        dto.setType(EventType.deposit);
        dto.setAmount(100.0);
        dto.setDestination("300");

        mockMvc.perform(post("/event")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.destination.id", is("300")))
                .andExpect(jsonPath("$.destination.balance", is(dto.getAmount())));
    }

    @Test
    void testCreateWithdraw() throws Exception {
        Account account = accountRepository.findById("300");
        account.setBalance(100.0);

        EventToCreateDTO dto = new EventToCreateDTO();
        dto.setType(EventType.withdraw);
        dto.setAmount(50.0);
        dto.setOrigin("300");
        
        Double expectedBalance = account.getBalance() - dto.getAmount();

        mockMvc.perform(post("/event")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.origin.id", is(account.getId())))
                .andExpect(jsonPath("$.origin.balance", is(expectedBalance)));
    }

    @Test
    void testCreateWithdrawWithoutOrigin() throws Exception {
        EventToCreateDTO dto = new EventToCreateDTO();
        dto.setType(EventType.withdraw);
        dto.setAmount(50.0);
        dto.setOrigin("999"); // Conta que não existe

        mockMvc.perform(post("/event")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isNotFound())
                .andExpect(content().string("0"));
    }
    
    @Test
    void testCreateTransfer() throws Exception {
        Account origin = accountRepository.findById("300");
        origin.setBalance(100.0);

        Account destination = accountRepository.save(new Account("100", 100.0, new ArrayList<>()));

        EventToCreateDTO dto = new EventToCreateDTO();
        dto.setType(EventType.transfer);
        dto.setAmount(50.0);
        dto.setOrigin("300");
        dto.setDestination("100");
        
        Double expectedOriginBalance = origin.getBalance() - dto.getAmount();
        Double expectedDestinationBalance = destination.getBalance() + dto.getAmount();

        mockMvc.perform(post("/event")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.origin.id", is(origin.getId())))
                .andExpect(jsonPath("$.origin.balance", is(expectedOriginBalance)))
                .andExpect(jsonPath("$.destination.id", is(destination.getId())))
                .andExpect(jsonPath("$.destination.balance", is(expectedDestinationBalance)));
    }
    
    @Test
    void testCreateTransferWithoutOrigin() throws Exception {
        EventToCreateDTO dto = new EventToCreateDTO();
        dto.setType(EventType.transfer);
        dto.setAmount(50.0);
        dto.setDestination("100");

        mockMvc.perform(post("/event")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isNotFound());
    }
    
    @Test
    void testCreateTransferWithoutDestination() throws Exception {
        EventToCreateDTO dto = new EventToCreateDTO();
        dto.setType(EventType.transfer);
        dto.setAmount(50.0);
        dto.setOrigin("300");

        mockMvc.perform(post("/event")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isNotFound());
    }
}
