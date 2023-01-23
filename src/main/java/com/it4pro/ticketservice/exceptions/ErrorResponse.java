package com.it4pro.ticketservice.exceptions;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.Accessors;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@ToString
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ErrorResponse {
    private String uid;
    private String requestId;
    private RestErrors error;

    public String reason() {
        return Optional.ofNullable(error)
            .flatMap(errors -> errors.getErrors()
                .stream()
                .map(RestError::getReason)
                .findFirst()
            )
            .orElse(null);
    }

    public String message() {
        return Optional.ofNullable(error)
            .flatMap(errors -> errors.getErrors()
                .stream()
                .map(RestError::getMessage)
                .filter(Objects::nonNull)
                .findFirst()
            )
            .orElse(null);
    }

    @ToString
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    public static class RestErrors {
        private List<RestError> errors;
    }


    @Data
    @Accessors(chain = true)
    public static class RestError {
        protected String reason;
        @JsonInclude(JsonInclude.Include.NON_NULL)
        protected String message;
        @JsonInclude(JsonInclude.Include.NON_NULL)
        protected Map<String, String> parameters;
    }


    @Accessors(chain = true)
    @Data
    @EqualsAndHashCode(callSuper = true)
    public static class RestErrorDev extends RestError {
        private String domain;
        private DomainSystemDetails domainSystemDetails;
    }


    @Accessors(chain = true)
    @Data
    @EqualsAndHashCode
    public static class DomainSystemDetails {
        private String externalSystemRequest;
        private String externalSystemMessage;
    }


    @Data
    @Accessors(chain = true)
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ErrorReasonReturn {
        private String reason;
        private HttpStatus status;

        public ErrorReasonReturn(ErrorReason reason, HttpStatus status) {
            this.reason = reason.getErrorKey();
            this.status = status;
        }
    }


    @Getter
    public enum ErrorReason {

        INVALID_TICKET_ID("invalidInput_ticketId"),
        REMOTE_HTTP_ERROR("serverError_httpRemote");

        private final String errorKey;

        ErrorReason(String errorKey) {
            this.errorKey = errorKey;
        }
    }
}
