package com.cpts422.GuapoBank.Controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.cpts422.GuapoBank.Entities.Notification;
import com.cpts422.GuapoBank.Entities.User;
import com.cpts422.GuapoBank.Repositories.NotificationRepository;
import com.cpts422.GuapoBank.Services.NotificationService;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import jakarta.servlet.http.HttpSession;
import org.springframework.ui.Model;
import java.util.*;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class NotificationControllerTest {

    @Mock
    private NotificationService notificationService;

    @Mock
    private NotificationRepository notificationRepository;

    @Mock
    private HttpSession session;

    @Mock
    private Model model;

    @InjectMocks
    private NotificationController notificationController;

    @Test
    void test_viewNotifications_loggedIn() {
        User loggedInUser = new User();
        when(session.getAttribute("loggedInUser")).thenReturn(loggedInUser);

        List<Notification> notifications = Arrays.asList(
                new Notification("Login at right now", loggedInUser),
                new Notification("A transfer of blahblahblah", loggedInUser)
        );

        when(notificationService.getNotificationsForUser(loggedInUser)).thenReturn(notifications);
        String result = notificationController.viewNotifications(session, model);
        assertEquals("InboxView", result);
        verify(model).addAttribute("notifications", notifications);
    }

    @Test
    void test_viewNotifications_notLoggedIn() {
        when(session.getAttribute("loggedInUser")).thenReturn(null);
        String result = notificationController.viewNotifications(session, model);
        assertEquals("redirect:/login", result);
    }

    @Test
    void test_markNotificationAsRead_success() {
        User loggedInUser = new User();
        loggedInUser.setId(1L);
        when(session.getAttribute("loggedInUser")).thenReturn(loggedInUser);
        Long notificationId = 1L;
        Notification notification = new Notification("Login at right now", loggedInUser);
        when(notificationRepository.findById(notificationId)).thenReturn(Optional.of(notification));
        String result = notificationController.markNotificationAsRead(notificationId, session);

        assertEquals("redirect:/notifications", result);
        assertTrue(notification.isRead());
        verify(notificationRepository).save(notification);
    }

    @Test
    void test_markNotificationAsRead_notLoggedIn() {
        when(session.getAttribute("loggedInUser")).thenReturn(null);
        Long notificationId = 1L;
        String result = notificationController.markNotificationAsRead(notificationId, session);
        assertEquals("redirect:/login", result);
    }

    @Test
    void test_markNotificationAsRead_noNotification() {
        User loggedInUser = new User();
        when(session.getAttribute("loggedInUser")).thenReturn(loggedInUser);
        Long notificationId = 1L;
        when(notificationRepository.findById(notificationId)).thenReturn(Optional.empty());
        String result = notificationController.markNotificationAsRead(notificationId, session);

        assertEquals("redirect:/notifications", result);
        verify(notificationRepository, never()).save(any(Notification.class));
    }

    @Test
    void test_markNotificationAsRead_wrongUserNotification() {
        User loggedInUser = new User();
        loggedInUser.setId(1L);
        when(session.getAttribute("loggedInUser")).thenReturn(loggedInUser);
        Long notificationId = 1L;
        User wrongUser = new User();
        wrongUser.setId(2L);
        Notification notification = new Notification("Login at right now", wrongUser);
        when(notificationRepository.findById(notificationId)).thenReturn(Optional.of(notification));
        String result = notificationController.markNotificationAsRead(notificationId, session);

        assertEquals("redirect:/notifications", result);
        assertFalse(notification.isRead());
        verify(notificationRepository, never()).save(notification);
    }
}