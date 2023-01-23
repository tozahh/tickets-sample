package com.it4pro.ticketservice.tickets.features.nodclient;

import okhttp3.Interceptor;
import okhttp3.Response;
import okhttp3.Request;
import org.springframework.http.HttpHeaders;

import java.io.IOException;
import java.util.Base64;

public class ExternalSystemAuthorizationInterceptor implements Interceptor {

    private String authorizationHeader;
    public ExternalSystemAuthorizationInterceptor(String clientId, String clientSecret) {
        authorizationHeader = "Basic " + Base64.getEncoder().encodeToString((clientId + ":" + clientSecret).getBytes());
    }

    @Override
    public Response intercept(Chain chain) throws IOException {

        Request request = chain.request()
            .newBuilder()
            .addHeader(HttpHeaders.AUTHORIZATION, authorizationHeader)
            .build();

        return chain.proceed(request);
    }
}
