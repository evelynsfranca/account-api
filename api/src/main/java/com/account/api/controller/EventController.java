package com.account.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.account.api.application.EventApplicationService;
import com.account.api.dto.event.EventToCreateDTO;
import com.account.api.dto.event.EventToResponseDTO;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class EventController {

    @Autowired
    private EventApplicationService eventApplicationService;
    
    @PostMapping("/event")
    public ResponseEntity<EventToResponseDTO> create(@RequestBody EventToCreateDTO dto) {
        System.out.println("Requested resource POST /event: " + dto);
        EventToResponseDTO result = eventApplicationService.create(dto);
        return new ResponseEntity<EventToResponseDTO>(result, HttpStatus.CREATED);
        
    }
}
