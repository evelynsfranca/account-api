package com.account.api.dto.event;

import java.io.Serializable;

import com.account.api.dto.account.AccountDTO;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class EventToResponseDTO implements Serializable {

    private AccountDTO destination;

    private AccountDTO origin;
}