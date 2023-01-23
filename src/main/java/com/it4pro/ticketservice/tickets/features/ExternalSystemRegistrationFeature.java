package com.it4pro.ticketservice.tickets.features;

import com.it4pro.ticketservice.tickets.TicketEntity;
import com.it4pro.ticketservice.tickets.features.nodclient.ExternalSystemClient;
import com.it4pro.ticketservice.tickets.features.nodclient.Order;
import com.it4pro.ticketservice.tickets.features.nodclient.OrderResponse;
import com.it4pro.ticketservice.tickets.features.utils.RetrofitCallHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static com.it4pro.ticketservice.tickets.features.nodclient.ExternalSystemClient.NOD_SERVICE_NAME;

@Component
@RequiredArgsConstructor
public class ExternalSystemRegistrationFeature implements TicketFeature {

    private final ExternalSystemClient externalSystemClient;
    private final RetrofitCallHandler retrofitCallHandler;
    public void postCreate(TicketEntity ticket) {
        OrderResponse orderResponse = retrofitCallHandler.call(externalSystemClient.registerTicket(new Order(ticket.getUuid())), NOD_SERVICE_NAME);
        //todo save external id
    }

    public void postDelete(TicketEntity ticket) {
        retrofitCallHandler.call(externalSystemClient.deleteTicket(ticket.getUuid()), NOD_SERVICE_NAME);
    }

    @Override
    public FeatureType getType() {
        return FeatureType.NOD_TICKET_REGISTRATION;
    }

}
