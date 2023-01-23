package com.it4pro.ticketservice.tickets.features;

import com.it4pro.ticketservice.tickets.Ticket;
import com.it4pro.ticketservice.tickets.TicketEntity;
import org.springframework.stereotype.Component;

@Component
public class NotificationFeature implements TicketFeature {
    public void postCreate(TicketEntity ticket) {
        //Publish event
    }

    public void postDelete(TicketEntity ticket) {
        //Publish event

    }

    @Override
    public FeatureType getType() {
        return FeatureType.TICKET_NOTIFICATION;
    }
}
