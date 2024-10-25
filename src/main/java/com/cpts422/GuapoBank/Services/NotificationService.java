package com.cpts422.GuapoBank.Services;

import com.cpts422.GuapoBank.Entities.Notification;
import com.cpts422.GuapoBank.Entities.User;

import java.util.List;

public interface NotificationService {
    public void sendNotification(String message, User user);

    public List<Notification> getNotificationsForUser(User user);
}
