package com.kalida.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kalida.model.Notification;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long>{

    List<Notification> findByUserUsernameAndIdGreaterThanAndReadedFalse(String userName, long id);
}
