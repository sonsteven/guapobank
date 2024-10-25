package com.cpts422.GuapoBank.Controllers;

import com.cpts422.GuapoBank.Entities.Notification;
import com.cpts422.GuapoBank.Entities.User;
import com.cpts422.GuapoBank.Repositories.NotificationRepository;
import com.cpts422.GuapoBank.Services.NotificationService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import java.util.List;
import java.util.Optional;

@Controller
public class NotificationController {
    @Autowired
    private NotificationService notificationService;

    @Autowired
    private NotificationRepository notificationRepository;


    @GetMapping("/notifications")
    public String viewNotifications(HttpSession session, Model model) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            return "redirect:/login";
        }

        List<Notification> notifications = notificationService.getNotificationsForUser(loggedInUser);
        model.addAttribute("notifications", notifications);

        return "InboxView";
    }

    @GetMapping("/notifications/{id}/read")
    public String markNotificationAsRead(@PathVariable Long id, HttpSession session) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            return "redirect:/login";
        }

        Optional<Notification> notificationOpt = notificationRepository.findById(id);
        if (notificationOpt.isPresent()) {
            Notification notification = notificationOpt.get();
            if (notification.getUser().getId().equals(loggedInUser.getId())) {
                notification.setRead(true);
                notificationRepository.save(notification);
            }
        }

        return "redirect:/notifications";
    }
}
