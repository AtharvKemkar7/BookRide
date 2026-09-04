package com.rideshare.notification.service;

import com.rideshare.notification.dto.SendNotificationRequest;
import com.rideshare.notification.entity.Notification;
import com.rideshare.notification.entity.NotificationChannel;
import com.rideshare.notification.entity.NotificationStatus;
import com.rideshare.notification.repository.NotificationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final NotificationDispatchService dispatchService;

    public NotificationService(
            NotificationRepository notificationRepository,
            NotificationDispatchService dispatchService) {
        this.notificationRepository = notificationRepository;
        this.dispatchService = dispatchService;
    }

    @Transactional
    public Notification send(SendNotificationRequest request) {
        Notification notification = new Notification();
        notification.setEventType(request.getEventType());
        notification.setRecipient(request.getRecipient());
        notification.setChannel(request.getChannel() == null ? NotificationChannel.PUSH : request.getChannel());
        notification.setTitle(request.getTitle());
        notification.setMessage(request.getMessage());
        notification.setTripId(request.getTripId());
        notification.setStatus(NotificationStatus.QUEUED);
        Notification saved = notificationRepository.save(notification);
        dispatchService.dispatch(saved.getId());
        return saved;
    }

    @Transactional(readOnly = true)
    public List<Notification> findAll() {
        return notificationRepository.findAllByOrderByCreatedAtDesc();
    }
}
