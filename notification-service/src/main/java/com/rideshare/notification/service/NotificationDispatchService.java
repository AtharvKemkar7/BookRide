package com.rideshare.notification.service;

import com.rideshare.notification.entity.Notification;
import com.rideshare.notification.entity.NotificationStatus;
import com.rideshare.notification.repository.NotificationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class NotificationDispatchService {

    private static final Logger log = LoggerFactory.getLogger(NotificationDispatchService.class);

    private final NotificationRepository notificationRepository;

    public NotificationDispatchService(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    @Async("notificationExecutor")
    public void dispatch(Long notificationId) {
        Notification notification = notificationRepository.findById(notificationId).orElse(null);
        if (notification == null) {
            return;
        }
        try {
            log.info("Mock {} to {} | {} | {}",
                    notification.getChannel(),
                    notification.getRecipient(),
                    notification.getTitle(),
                    notification.getMessage());
            notification.setStatus(NotificationStatus.SENT);
            notification.setSentAt(Instant.now());
        } catch (Exception ex) {
            notification.setStatus(NotificationStatus.FAILED);
            log.warn("Failed to mock-send notification {}: {}", notificationId, ex.getMessage());
        }
        notificationRepository.save(notification);
    }
}
