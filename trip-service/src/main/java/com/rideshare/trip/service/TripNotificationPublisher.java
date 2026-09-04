package com.rideshare.trip.service;

import com.rideshare.trip.client.NotificationClient;
import com.rideshare.trip.dto.NotificationRequest;
import com.rideshare.trip.entity.Trip;
import com.rideshare.trip.entity.TripStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class TripNotificationPublisher {

    private static final Logger log = LoggerFactory.getLogger(TripNotificationPublisher.class);

    private final NotificationClient notificationClient;

    public TripNotificationPublisher(NotificationClient notificationClient) {
        this.notificationClient = notificationClient;
    }

    @Async("tripNotificationExecutor")
    public void notifyStatusChange(Trip trip) {
        if (trip.getStatus() != TripStatus.REQUESTED
                && trip.getStatus() != TripStatus.COMPLETED
                && trip.getStatus() != TripStatus.IN_PROGRESS) {
            return;
        }
        NotificationRequest request = new NotificationRequest();
        request.setEventType(trip.getStatus().name());
        request.setRecipient(trip.getPassengerName() == null ? "passenger-" + trip.getPassengerId() : trip.getPassengerName());
        request.setChannel("PUSH");
        request.setTripId(trip.getId());
        request.setTitle(titleFor(trip.getStatus()));
        request.setMessage(messageFor(trip));
        try {
            notificationClient.send(request);
        } catch (Exception ex) {
            log.warn("Notification dispatch failed for trip {}: {}", trip.getId(), ex.getMessage());
        }
    }

    private String titleFor(TripStatus status) {
        return switch (status) {
            case REQUESTED -> "Ride Requested";
            case IN_PROGRESS -> "Trip Started";
            case COMPLETED -> "Trip Completed";
            default -> "Ride Update";
        };
    }

    private String messageFor(Trip trip) {
        return switch (trip.getStatus()) {
            case REQUESTED -> "Looking for nearby drivers from " + trip.getPickupAddress();
            case IN_PROGRESS -> "Your trip to " + trip.getDropoffAddress() + " has started";
            case COMPLETED -> "You arrived at " + trip.getDropoffAddress() + ". Fare $" + trip.getFinalFare();
            default -> "Trip status is now " + trip.getStatus();
        };
    }
}
