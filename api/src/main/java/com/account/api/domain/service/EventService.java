package com.account.api.domain.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.account.api.domain.enumeration.EventType;
import com.account.api.domain.model.Account;
import com.account.api.domain.model.Event;
import com.account.api.repository.EventRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EventService {
    
    @Autowired
    private EventRepository eventRepository;
    
    @Autowired
    private AccountService accountService;

    public Event create(Event event) {

        validate(event);

        Account origin = event.getOrigin();
        Account destination = event.getDestination();

        UUID uuid = UUID.randomUUID();
        String uuidAsString = uuid.toString();

        event.setId(uuidAsString);

        if (origin != null) {
            accountService.updateAccount(origin, event);            
        } 

        if (destination != null) {    
            accountService.updateAccount(destination, event); 
        } 

        eventRepository.save(event);

        return event;
    }

    private void validate(Event event) {        
        if (event.getType().equals(EventType.withdraw)) {

            if (event.getOrigin() == null) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "A conta deve ser informada."); 
            }
        }
        
        if (event.getType().equals(EventType.transfer)) {

            if (event.getDestination() == null) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "A conta de destino deve ser informada."); 
            }
            
            if (event.getOrigin() == null) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "A conta deve ser informada."); 
            }

            if (event.getOrigin().getBalance() < event.getAmount()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Saldo insuficiente.");

            }
        }
    }
    
    public void reset() {
        eventRepository.reset();
    }
}
