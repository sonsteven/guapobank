package com.cpts422.GuapoBank.Entities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;

class NotificationTest {

    private Notification notification;

    private String message;

    private User user;

    @BeforeEach
    void setUp() {
        message = "Default message";
        user = new User();
        notification = new Notification(message, user);
    }

    @Test
    void test_notificationConstructor() {
        Notification defaultNotification = new Notification();
        assertNotNull(defaultNotification);
        assertNotNull(defaultNotification.getCreatedAt());
        assertFalse(defaultNotification.isRead());
    }

    @Test
    void test_notificationParameters() {
        assertEquals(message, notification.getMessage());
        assertEquals(user, notification.getUser());
        assertNotNull(notification.getCreatedAt());
        assertFalse(notification.isRead());
    }

    @Test
    void test_gettersAndSetters() {
        boolean readStatus = true;
        notification.setRead(readStatus);
        notification.setUser(user);

        assertEquals(readStatus, notification.isRead());
        assertEquals(user, notification.getUser());
    }

    @Test
    void test_getId() throws NoSuchFieldException, IllegalAccessException {
        Long expectedId = 1L;
        Field idField = Notification.class.getDeclaredField("id");
        idField.setAccessible(true);
        idField.set(notification, expectedId);
        Long actualId = notification.getId();

        assertEquals(expectedId, actualId);
    }
}