package com.cpts422.GuapoBank.Repositories;

import com.cpts422.GuapoBank.Entities.Notification;
import com.cpts422.GuapoBank.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findByUser(User user);
}
