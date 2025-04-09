package com.account.api.repository;

import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Repository;

import com.account.api.domain.model.Event;

@Repository
public class EventRepositoryImpl implements EventRepository {

    private Set<Event> events = new HashSet<>();

    public EventRepositoryImpl() { }
    
    @Override
    public Event save(Event event) {
        events.add(event);
        return event;
    }

    @Override
    public void reset() {
        events = new HashSet<>();
    }
}
