package com.account.api.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.account.api.domain.enumeration.EventType;
import com.account.api.domain.model.Account;
import com.account.api.domain.model.Event;
import com.account.api.domain.service.AccountService;
import com.account.api.domain.service.EventService;
import com.account.api.dto.event.EventToCreateDTO;
import com.account.api.dto.event.EventToResponseDTO;
import com.account.api.mapper.EventMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EventApplicationService {
    
    @Autowired
    private EventService eventService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private EventMapper eventMapper;

    public EventToResponseDTO create(EventToCreateDTO dto) {

        Event event = eventMapper.toEntityEvent(dto);
        Account origin = accountService.getAccount(event.getOrigin().getId());
        Account destination = accountService.getAccount(event.getDestination().getId());

        if (event.getType().equals(EventType.deposit) && destination == null) {
            destination = accountService.createAccountFromEvent(event);
        } 
        
        event.setOrigin(origin);
        event.setDestination(destination);

        Event entity = eventService.create(event);

        return eventMapper.toResponseDTO(entity);
    }

    public void reset() {
        eventService.reset();
    }
}
