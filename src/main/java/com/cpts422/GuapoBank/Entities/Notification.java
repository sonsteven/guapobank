package com.cpts422.GuapoBank.Entities;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String message;
    private LocalDateTime createdAt;
    private boolean read;

    @ManyToOne
    private User user;

    public Notification() {
        this.createdAt = LocalDateTime.now();
        this.read = false;
    }

    public Notification(String message, User user) {
        this.message = message;
        this.user = user;
        this.createdAt = LocalDateTime.now();
        this.read = false;
    }

    public Long getId() {
        return id;
    }

    public String getMessage() {
        return message;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public boolean isRead() {
        return read;
    }

    public void setRead(boolean read) {
        this.read = read;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
