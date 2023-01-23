package com.it4pro.ticketservice.tickets.features.nodclient;

public class RemoteServiceException extends RuntimeException {
    public RemoteServiceException(String msg) {
        super(msg);
    }
    public RemoteServiceException(String msg, Throwable throwable) {
        super(msg, throwable);
    }
}
