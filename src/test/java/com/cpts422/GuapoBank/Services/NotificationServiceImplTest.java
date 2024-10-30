package com.cpts422.GuapoBank.Services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.cpts422.GuapoBank.Entities.Notification;
import com.cpts422.GuapoBank.Entities.User;
import com.cpts422.GuapoBank.Repositories.NotificationRepository;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import java.util.*;

@ExtendWith(MockitoExtension.class)
class NotificationServiceImplTest {

    @Mock
    private NotificationRepository notificationRepository;

    @InjectMocks
    private NotificationServiceImpl notificationService;

    @Test
    void test_sendNotification_success() {
        String message = "Login at right now";
        User user = new User();
        Notification notification = new Notification(message, user);
        notificationService.sendNotification(message, user);

        ArgumentCaptor<Notification> notificationCaptor = ArgumentCaptor.forClass(Notification.class);
        verify(notificationRepository).save(notificationCaptor.capture());
        Notification savedNotification = notificationCaptor.getValue();
        assertEquals(message, savedNotification.getMessage());
        assertEquals(user, savedNotification.getUser());
    }

    @Test
    void test_sendNotification_saveException() {
        String message = "Login at right now";
        User user = new User();
        doThrow(new RuntimeException("Notification repo error"))
                .when(notificationRepository).save(any(Notification.class));

        Exception exception = assertThrows(RuntimeException.class, () -> {
            notificationService.sendNotification(message, user);
        });
        assertEquals("Notification repo error", exception.getMessage());
    }

    @Test
    void test_getNotificationsForUser_success() {
        User user = new User();
        List<Notification> notifications = Arrays.asList(
                new Notification("Login at right now", user),
                new Notification("A transfer of blahblahblah", user)
        );

        when(notificationRepository.findByUser(user)).thenReturn(notifications);
        List<Notification> result = notificationService.getNotificationsForUser(user);
        assertEquals(2, result.size());
        verify(notificationRepository).findByUser(user);
    }

    @Test
    void test_getNotificationsForUser_emptyList() {
        User user = new User();
        when(notificationRepository.findByUser(user)).thenReturn(Collections.emptyList());
        List<Notification> result = notificationService.getNotificationsForUser(user);
        assertTrue(result.isEmpty());
        verify(notificationRepository).findByUser(user);
    }
}