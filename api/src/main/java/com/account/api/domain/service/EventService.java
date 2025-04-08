package com.account.api.domain.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.account.api.domain.model.Account;
import com.account.api.domain.model.Event;
import com.account.api.exception.BusinessException;
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

        if (event.getType() != null) {

            switch (event.getType()) {
                case withdraw -> {
                    if (event.getOrigin() == null) {
                        throw new BusinessException(HttpStatus.NOT_FOUND, "0");
                    }
                }

                case transfer -> {
                    if (event.getOrigin() == null || event.getDestination() == null) {
                        throw new BusinessException(HttpStatus.NOT_FOUND, "0");
                    }
                }

                default -> {}
            }

        }
    }

    public void reset() {
        eventRepository.reset();
    }
}
