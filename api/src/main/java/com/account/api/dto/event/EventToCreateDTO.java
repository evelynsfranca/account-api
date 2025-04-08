package com.account.api.dto.event;

import java.io.Serializable;

import org.springframework.lang.Nullable;

import com.account.api.domain.enumeration.EventType;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class EventToCreateDTO implements Serializable {

    @NotNull(message = "Informe o tipo do evento.")
    private EventType type;
    
    @NotNull(message = "Informe o valor.")
    private Double amount;

    @Nullable
    private String origin;

    @Nullable
    private String destination;
} 