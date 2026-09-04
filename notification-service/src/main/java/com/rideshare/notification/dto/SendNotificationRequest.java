package com.rideshare.notification.dto;

import com.rideshare.notification.entity.NotificationChannel;
import jakarta.validation.constraints.NotBlank;

public class SendNotificationRequest {

    @NotBlank
    private String eventType;

    @NotBlank
    private String recipient;

    private NotificationChannel channel = NotificationChannel.PUSH;

    @NotBlank
    private String title;

    @NotBlank
    private String message;

    private Long tripId;

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public NotificationChannel getChannel() {
        return channel;
    }

    public void setChannel(NotificationChannel channel) {
        this.channel = channel;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getTripId() {
        return tripId;
    }

    public void setTripId(Long tripId) {
        this.tripId = tripId;
    }
}
