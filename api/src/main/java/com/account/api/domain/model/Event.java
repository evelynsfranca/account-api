package com.account.api.domain.model;

import java.io.Serializable;

import com.account.api.domain.enumeration.EventType;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = {"origin", "destination"})
public class Event implements Serializable {
    
    private static final long serialVersionUID = 1L;

    private String id;

    @Enumerated(EnumType.STRING)
    private EventType type;
    
    private Double amount;

    @JsonIgnore 
    private Account origin;

    @JsonIgnore 
    private Account destination;
}
