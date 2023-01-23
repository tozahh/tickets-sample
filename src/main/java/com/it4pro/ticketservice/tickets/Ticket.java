package com.it4pro.ticketservice.tickets;

import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;


@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Ticket {

    String id;
    @Length(min = 1, max = 256)
    @NotNull
    String name;
    @NotNull
    TicketState state;
    @NotNull
    TicketEntity.TicketType type;
}
