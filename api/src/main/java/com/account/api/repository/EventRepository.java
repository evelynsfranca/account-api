package com.account.api.repository;

import com.account.api.domain.model.Event;

public interface EventRepository {
    
    void save(Event event);
    
    void reset();
}
