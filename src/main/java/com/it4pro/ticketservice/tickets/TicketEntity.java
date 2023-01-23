package com.it4pro.ticketservice.tickets;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Entity(name="ticket")
public class TicketEntity {

    public TicketEntity(String name, String uuid, int state, TicketType type) {
        this.name = name;
        this.state = state;
        this.uuid = uuid;
        this.type = type;
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column
    private String uuid;
    @Column
    private String name;
    @Column
    private int state;
    @Column
    private boolean registeredInNod;
    @Column
    @Enumerated
    private TicketType type;


    enum TicketType {
        SINGLE, PERIOD;
    }
}
