package com.cpts422.GuapoBank.Controllers;

import com.cpts422.GuapoBank.Entities.User;
import com.cpts422.GuapoBank.Services.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@Controller
public class AdminController {

    @Autowired
    private UserService userService;

    @GetMapping("/admin/home")
    public String adminHome(HttpSession session, Model model) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser == null || !loggedInUser.getRole().equals("Admin")) {
            return "redirect:/login";
        }

        Iterable<User> users = userService.findAll();
        model.addAttribute("users", users);
        model.addAttribute("loggedInUser", loggedInUser);

        return "AdminHome";
    }

    @GetMapping("/admin/user/{id}/accounts")
    public String ViewUserAccounts(@PathVariable Long id, Model model, HttpSession session) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser == null || !loggedInUser.getRole().equals("Admin")) {
            return "redirect:/login";
        }

        Optional<User> userOpt = userService.findById(id);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            model.addAttribute("user", user);
            model.addAttribute("accounts", user.getAccounts());
            return "ViewUserAccounts";
        }

        return "redirect:/admin/home";
    }
}
