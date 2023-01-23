package com.it4pro.ticketservice.tickets;

import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotNull;


@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Ticket {

    String id;
    String name;
    TicketState state;
    @NotNull
    TicketEntity.TicketType type;
}
