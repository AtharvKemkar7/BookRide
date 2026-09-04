package com.rideshare.notification.controller;

import com.rideshare.notification.dto.SendNotificationRequest;
import com.rideshare.notification.entity.Notification;
import com.rideshare.notification.service.NotificationService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @PostMapping("/send")
    public ResponseEntity<Notification> send(@Valid @RequestBody SendNotificationRequest request) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(notificationService.send(request));
    }

    @GetMapping
    public List<Notification> list() {
        return notificationService.findAll();
    }
}
