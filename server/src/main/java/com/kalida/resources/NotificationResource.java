package com.kalida.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.kalida.exception.DomainException;
import com.kalida.model.Notification;
import com.kalida.model.User;
import com.kalida.service.NotificationService;


@RestController
@RequestMapping("/notification")
public class NotificationResource extends Controllable{

    @Autowired
    private NotificationService notificationService;

    @GetMapping
    public List<Notification> findNotifications(@RequestParam Long startId){
        User user = getUser();
        return notificationService.findNotifications(user, startId);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void setNotificationReaded(@RequestBody String id){
        DomainException.validate(id != null && !id.isEmpty(), "Invalid notification ID", HttpStatus.BAD_REQUEST);
        Notification notification = notificationService.findById(Long.parseLong(id));
        notification.setReaded(true);
        notificationService.save(notification);
    }
    
}
