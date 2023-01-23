package com.it4pro.ticketservice.exceptions;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

public interface ErrorFactory {
    ErrorResponse.RestError getError(String reasons, String domain, String messages);

    ErrorResponse.RestError getError(String reasons, String domain, String messages, Map<String, String> parameters);

    @Configuration
    class ErrorFactoryConfiguration {

        @Bean
        @ConditionalOnProperty(prefix = "service.rest", value = "error-format-extended", havingValue = "true")
        ErrorFactory errorFactoryDev() {
            return new ErrorFactoryDev();
        }

        @Bean
        @ConditionalOnMissingBean(ErrorFactory.class)
        ErrorFactory errorFactory() {
            return new ErrorFactoryProd();
        }
    }


    class ErrorFactoryDev implements ErrorFactory {

        @Override
        public ErrorResponse.RestError getError(String reason, String domain, String message) {
            return new ErrorResponse.RestErrorDev()
                .setDomain(domain)
                .setMessage(message)
                .setReason(reason);
        }

        @Override
        public ErrorResponse.RestError getError(String reason, String domain, String message, Map<String, String> parameters) {
            return new ErrorResponse.RestErrorDev()
                .setDomain(domain)
                .setMessage(message)
                .setReason(reason)
                .setParameters(parameters);
        }
    }


    class ErrorFactoryProd implements ErrorFactory {

        @Override
        public ErrorResponse.RestError getError(String reason, String domain, String message) {
            return new ErrorResponse.RestError().setReason(reason);
        }

        @Override
        public ErrorResponse.RestError getError(String reason, String domain, String message, Map<String, String> parameters) {
            return new ErrorResponse.RestError().setReason(reason).setParameters(parameters);
        }
    }
}
