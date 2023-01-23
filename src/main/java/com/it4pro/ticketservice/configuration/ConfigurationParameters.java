package com.it4pro.ticketservice.configuration;

import com.it4pro.ticketservice.tickets.features.FeatureType;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.net.URI;
import java.time.Duration;
import java.util.LinkedList;
import java.util.List;

@Data
@ConfigurationProperties
public class ConfigurationParameters {
    private List<FeatureType> features = new LinkedList<>();
    private Nod nod = new Nod();

    @Data
    public class Nod extends RestConfiguration {
        private String clientId;
        private String clientSecret;
        private URI uri;
    }

    @Data
    public static class RestConfiguration {
        private URI uri;
        private Duration connectTimeout = Duration.ofSeconds(5);
        private Duration readTimeout = Duration.ofSeconds(5);
        private Duration writeTimeout = Duration.ofSeconds(5);
    }
}
