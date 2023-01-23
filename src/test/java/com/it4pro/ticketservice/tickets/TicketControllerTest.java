package com.it4pro.ticketservice.tickets;

import io.restassured.RestAssured;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TicketControllerTest {

    @LocalServerPort
    int port;

    @Autowired
    private TicketService ticketService;

    private Ticket ticketUnderTests;

    @Before
    public void setupDb() {
        ticketUnderTests = prepareTicket("1 zone ticket", TicketEntity.TicketType.SINGLE, TicketState.ACTIVE);
    }

    @Test
    public void testGetTicket() {
        Ticket ticket = RestAssured.given().port(port)
            .basePath("api/tickets/{uuid}")
            .pathParam("uuid", ticketUnderTests.getId())
            .get()
            .then()
            .statusCode(HttpStatus.OK.value())
            .extract().as(Ticket.class);
        Assert.assertEquals(ticketUnderTests, ticket);
    }

    @Test
    public void testCreateTicketOk() {
        //todo
        Assert.fail();
    }
    @Test
    public void testCreateTicketInvalidInput() {
        //todo
        Assert.fail();
    }
    @Test
    public void testDeleteTicketOk() {
        //todo
        Assert.fail();
    }
    @Test
    public void testDeleteTicketNotfound() {
        //todo
        Assert.fail();
    }

    private Ticket prepareTicket(String name, TicketEntity.TicketType ticketType, TicketState state) {
        Ticket ticket = new Ticket();
        ticket.setName(name);
        ticket.setType(ticketType);
        ticket.setState(state);
        return ticketService.createTicket(ticket);
    }

}
