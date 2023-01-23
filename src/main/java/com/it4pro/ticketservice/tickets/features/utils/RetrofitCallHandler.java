package com.it4pro.ticketservice.tickets.features.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.it4pro.ticketservice.tickets.features.nodclient.RemoteServiceException;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import retrofit2.Call;
import retrofit2.Response;

import java.io.IOException;
import java.net.SocketTimeoutException;

@Slf4j
@RequiredArgsConstructor
public class RetrofitCallHandler {

    public <T> T call(Call<T> call, String serviceName) throws RemoteServiceException {
        T responseBody;
        try {
            Response<T> response = call.execute();
            if (!response.isSuccessful()) {
                String errorBody = response.errorBody() != null ? response.errorBody().string() : null;

                log.warn("Request failed with response {} - {}!\n {}", response.code(), errorBody, RequestToCurlFormatter.format(call.request()));
                throw new RemoteServiceException(RequestToCurlFormatter.format(call.request()));
            }

            responseBody = response.body();
        } catch (SocketTimeoutException e) {
            log.warn(String.format("Timed out waiting for response from %s! %s", serviceName, RequestToCurlFormatter.format(call.request())));
            throw new RemoteServiceException("Timed out waiting for response from " + serviceName, e);
        } catch (IOException e) {
            log.error(String.format("Failed to reach service %s! %s", serviceName, RequestToCurlFormatter.format(call.request())), e);
            throw new RemoteServiceException("Failed to reach remote service", e);
        }

        log.debug("Successfully got response from {}: {}", serviceName, responseBody);
        return responseBody;
    }

    @Data
    public static class ExternalRestError {
        String detail;
        int status;
        String title;
        String type;
    }

}