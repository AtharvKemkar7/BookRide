package com.rideshare.trip.client;

import com.rideshare.trip.dto.NotificationRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(
        name = "notification-service",
        url = "${rideshare.notification-service.url}",
        configuration = com.rideshare.trip.config.FeignConfig.class
)
public interface NotificationClient {

    @PostMapping("/api/notifications/send")
    void send(@RequestBody NotificationRequest request);
}
