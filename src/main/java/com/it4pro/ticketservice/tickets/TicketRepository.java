package com.it4pro.ticketservice.tickets;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketRepository extends JpaRepository<TicketEntity, Long> {

    TicketEntity findByUuid(String uuid);

}
