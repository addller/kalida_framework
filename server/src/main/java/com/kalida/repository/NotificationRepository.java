package com.kalida.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kalida.model.Notification;

public interface NotificationRepository extends JpaRepository<Notification, Long>{

    List<Notification> findByUserUsernameAndIdGreaterThanAndReadedFalse(String userName, long id);
}
