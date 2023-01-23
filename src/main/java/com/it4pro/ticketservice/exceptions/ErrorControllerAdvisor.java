package com.it4pro.ticketservice.exceptions;

import com.it4pro.ticketservice.tickets.features.nodclient.RemoteServiceException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@ControllerAdvice
@RequiredArgsConstructor
class ErrorControllerAdvisor {

    private static final String DOMAIN_TICKETS = "Tickets";

    private final ErrorFactory errorFactory;

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgumentException(Exception e) {
        return build(HttpStatus.NOT_FOUND, ErrorResponse.ErrorReason.INVALID_TICKET_ID.getErrorKey(), e);
    }
    @ExceptionHandler(RemoteServiceException.class)
    public ResponseEntity<ErrorResponse> handleRemoteServiceException(Exception e) {
        return build(HttpStatus.SERVICE_UNAVAILABLE, ErrorResponse.ErrorReason.REMOTE_HTTP_ERROR.getErrorKey(), e);
    }

    private ResponseEntity<ErrorResponse> build(HttpStatus status, String reason, Exception e) {
        Map<String, String> parameters = null;
        List<ErrorResponse.RestError> errors = List.of(errorFactory.getError(reason, DOMAIN_TICKETS, e.getMessage()));
        return ResponseEntity.status(status)
                            .contentType(MediaType.APPLICATION_JSON)
                            .body(new ErrorResponse(UUID.randomUUID().toString(), null, new ErrorResponse.RestErrors(errors)));
    }

//    private ResponseEntity<ErrorResponse> build(HttpStatus status, List<ErrorResponse.RestError> errors, Throwable e) {
//        String uid = DateTimeFormatter.ofPattern("yyyyMMddhhmmss").format(LocalDateTime.now())
//            + RandomStringUtils.randomAlphabetic(5);
//
//        return RequestIdHolder.getRequestId()
//            .map(requestId -> {
//                var errorResponse = new ErrorResponse(uid, requestId.requestId(), new ErrorResponse.RestErrors(errors));
//                return ResponseEntity.status(status)
//                    .header("uid", uid)
//                    .contentType(MediaType.APPLICATION_JSON)
//                    .body(errorResponse);
//            })
//            .flatMap(a -> logResponse(a, e));
//    }
}

