package com.it4pro.ticketservice.tickets;

import com.it4pro.ticketservice.tickets.features.FeaturesChain;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class TicketService {

    private final TicketRepository ticketRepository;
    private final FeaturesChain featuresChain;

    public Ticket createTicket(Ticket ticket) {
        TicketEntity ticketEntity = ticketRepository.saveAndFlush(new TicketEntity(ticket.getName(), UUID.randomUUID().toString(), ticket.getState().getStateId(), ticket.getType()));

        featuresChain.applyPostCreate(ticketEntity);
        return fromTicketEntity(ticketEntity);
    }

    public Ticket updateTicket(String uuid, Ticket ticket) {
        TicketEntity ticketEntity = getTicketEntity(uuid);

        if (ticket.getName() != null) {
            ticketEntity.setName(ticket.getName());
        }
        if (ticket.getState() != null) {
            ticketEntity.setState(ticket.getState().getStateId());
        }

        ticketEntity = ticketRepository.save(ticketEntity);

        return fromTicketEntity(ticketEntity);
    }

    public Collection<Ticket> getTickets() {
        return ticketRepository.findAll().stream()
            .map(this::fromTicketEntity)
            .collect(Collectors.toList());
    }

    public Ticket getTicket(String uuid) {
        return fromTicketEntity(getTicketEntity(uuid));
    }

    public TicketEntity getTicketEntity(String uuid) {
        return Optional.ofNullable(ticketRepository.findByUuid(uuid)).
            orElseThrow(() -> new IllegalArgumentException("Ticket not found. uuid: " + uuid));
    }

    private Ticket fromTicketEntity(TicketEntity ticketEntity) {
        return new Ticket(ticketEntity.getUuid(),
                          ticketEntity.getName(),
                          TicketState.valueOf(ticketEntity.getState()),
                          ticketEntity.getType());
    }
}
