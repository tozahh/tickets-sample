package com.it4pro.ticketservice.tickets.features;

import com.it4pro.ticketservice.tickets.Ticket;
import com.it4pro.ticketservice.tickets.TicketEntity;

public interface TicketFeature {
    void postCreate(TicketEntity ticket);

    void postDelete(TicketEntity ticket);

    FeatureType getType();
}
