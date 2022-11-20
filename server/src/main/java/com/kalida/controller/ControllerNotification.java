package com.kalida.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kalida.model.Notification;
import com.kalida.security.User;
import com.kalida.service.NotificationService;


@RestController
@RequestMapping("/notification")
public class ControllerNotification extends Controllable{

    @Autowired
    private NotificationService notificationService;

    @GetMapping
    public List<Notification> findNotifications(@RequestParam Long startId){
        User user = getUser();
        return notificationService.findNotifications(user, startId);
    }

    @PutMapping
    public ResponseEntity<Void> setNotificationReaded(@RequestBody String id){
        Notification notification = notificationService.findById(Long.parseLong(id));
        notification.setReaded(true);
        notificationService.save(notification);
        return noContent();
    }
    
}
