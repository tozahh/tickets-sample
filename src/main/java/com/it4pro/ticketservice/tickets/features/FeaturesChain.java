package com.it4pro.ticketservice.tickets.features;

import com.it4pro.ticketservice.configuration.ConfigurationParameters;
import com.it4pro.ticketservice.tickets.Ticket;
import com.it4pro.ticketservice.tickets.TicketEntity;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class FeaturesChain {
    List<TicketFeature> features;
    ConfigurationParameters configuration;

    public void applyPostCreate(TicketEntity ticket) {
        features.stream()
            .filter(feature -> isApplicable(feature.getType()))
            .forEach(feature -> feature.postCreate(ticket));
    }

    public void applyPostDelete(TicketEntity ticket) {
        features.stream()
            .filter(feature -> isApplicable(feature.getType()))
            .forEach(feature -> feature.postDelete(ticket));
    }

    private boolean isApplicable(FeatureType featureType) {
        return configuration.getFeatures().contains(featureType);
    }
}
