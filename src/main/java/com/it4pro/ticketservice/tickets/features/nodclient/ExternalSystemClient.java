package com.it4pro.ticketservice.tickets.features.nodclient;


import retrofit2.Call;
import retrofit2.http.*;

public interface ExternalSystemClient {
    String NOD_SERVICE_NAME = "NodService";

    @POST("ext/orders")
    Call<OrderResponse> registerTicket(@Body Order order);

    @DELETE("ext/orders/{id}")
    Call<Void> deleteTicket(@Path("id") String id);
}
