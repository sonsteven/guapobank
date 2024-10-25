package com.cpts422.GuapoBank.Services;

import com.cpts422.GuapoBank.Entities.Notification;
import com.cpts422.GuapoBank.Entities.User;
import com.cpts422.GuapoBank.Repositories.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class NotificationServiceImpl implements NotificationService{
    @Autowired
    private NotificationRepository notificationRepository;

    @Override
    public void sendNotification(String message, User user) {
        Notification notification = new Notification(message, user);
        notificationRepository.save(notification);
    }

    @Override
    public List<Notification> getNotificationsForUser(User user) {
        return notificationRepository.findByUser(user);
    }
}
