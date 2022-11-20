package com.kalida.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.kalida.exception.DomainException;
import com.kalida.model.Notification;
import com.kalida.repository.NotificationRepository;
import com.kalida.security.User;

@Service
public class NotificationService {
    
    @Autowired
    private NotificationRepository notificationRepository;

    public List<Notification> findNotifications(User user, Long startId) {
        return notificationRepository
            .findByUserUsernameAndIdGreaterThanAndReadedFalse(user.getUsername(), startId);
    }

    public Notification findById(Long id) {
        return notificationRepository.findById(id)
            .orElseThrow(() -> new DomainException("Notification not found by id"+id, HttpStatus.NOT_FOUND));
    }

    public void save(Notification notification) {
        notificationRepository.save(notification);
    }

    
}
