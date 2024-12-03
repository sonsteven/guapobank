package com.cpts422.GuapoBank.Integration;

import com.cpts422.GuapoBank.Controllers.NotificationController;
import com.cpts422.GuapoBank.Services.NotificationService;
import com.cpts422.GuapoBank.Services.NotificationServiceImpl;
import com.cpts422.GuapoBank.Repositories.NotificationRepository;
import com.cpts422.GuapoBank.Entities.Notification;
import com.cpts422.GuapoBank.Entities.User;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.ui.Model;

import jakarta.servlet.http.HttpSession;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@WebMvcTest(NotificationController.class)
@ExtendWith(MockitoExtension.class)
@Import(NotificationServiceImpl.class)
public class TestPairNotificationControllerNotificationService {

    @Autowired
    private NotificationController notificationController;

    @Autowired
    private NotificationService notificationService;

    // Mocking all other dependencies
    @MockBean
    private NotificationRepository notificationRepository;

    @Mock
    private HttpSession session;

    @Test
    public void testViewNotifications() {
        User loggedInUser = new User();
        loggedInUser.setId(1L);
        when(session.getAttribute("loggedInUser")).thenReturn(loggedInUser);
        Notification notification1 = new Notification("Message 1", loggedInUser);
        Notification notification2 = new Notification("Message 2", loggedInUser);
        List<Notification> notifications = Arrays.asList(notification1, notification2);
        when(notificationRepository.findByUser(loggedInUser)).thenReturn(notifications);

        Model model = mock(Model.class);
        String viewName = notificationController.viewNotifications(session, model);

        assertEquals("InboxView", viewName);
        verify(notificationRepository).findByUser(loggedInUser);
        verify(model).addAttribute("notifications", notifications);
    }

    @Test
    public void testMarkNotificationAsRead() {
        User loggedInUser = new User();
        loggedInUser.setId(1L);
        when(session.getAttribute("loggedInUser")).thenReturn(loggedInUser);

        Notification notification = new Notification("Message", loggedInUser);
        notification.setRead(false);
        when(notificationRepository.findById(1L)).thenReturn(Optional.of(notification));
        String viewName = notificationController.markNotificationAsRead(1L, session);

        assertEquals("redirect:/notifications", viewName);
        assertTrue(notification.isRead());
        verify(notificationRepository).save(notification);
    }
}
