package com.it4pro.ticketservice.tickets;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.Arrays;


@RequiredArgsConstructor
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public enum TicketState {

    INACTIVE(0), ACTIVE(1);
    int stateId;


    public static TicketState valueOf(int stateId) {
        return Arrays.stream(values())
            .filter(s -> s.getStateId() == stateId)
            .findFirst()
            .orElseThrow(IllegalArgumentException::new);
    }
}
