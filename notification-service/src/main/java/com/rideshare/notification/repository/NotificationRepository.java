package com.rideshare.notification.repository;

import com.rideshare.notification.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

    List<Notification> findAllByOrderByCreatedAtDesc();

    List<Notification> findByRecipientOrderByCreatedAtDesc(String recipient);
}
