package com.cpts422.GuapoBank.Controllers;

import com.cpts422.GuapoBank.Entities.Transaction;
import com.cpts422.GuapoBank.Entities.User;
import com.cpts422.GuapoBank.Repositories.AccountRepository;
import com.cpts422.GuapoBank.Services.AccountService;
import com.cpts422.GuapoBank.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpSession;

@Controller
public class HomeController {
    @Autowired
    private UserService userService;
    private AccountService accountService;

    public HomeController(UserService userService, AccountService accountService) {
        this.userService = userService;
        this.accountService = accountService;
    }

    // Handles routing for the root URL, redirects based on user login status.
    @GetMapping("/")
    public String index(HttpSession session) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser != null) {
            if (loggedInUser.getRole().equals("Admin")) {
                return "redirect:/admin/home";
            }
            else {
                return "redirect:/home";
            }
        }
        else {
            return "redirect:/login";
        }
    }

    // Handles GET request for the Login page, redirects based on user login status.
    @GetMapping("/login")
    public String login(HttpSession session, Model model) {
        // model.addAttribute("transaction", new Transaction());
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser != null) {
            if (loggedInUser.getRole().equals("Admin")) {
                return "redirect:/admin/home";
            }
            else{
                return "redirect:/home";
            }
        }
        return "Login";
    }

    // Handles POST request for user authentication, redirects based on successful auth or invalid credentials.
    @PostMapping("/login")
    public String authenticate(@RequestParam String username, @RequestParam String password, Model model, HttpSession session) {
        User user = userService.authenticate(username, password);
        if (user != null) {
            session.setAttribute("loggedInUser", user);
            if (user.getRole().equals("Admin")) {
                return "redirect:/admin/home";
            }
            else {
                return "redirect:/home";
            }
        }
        else {
            model.addAttribute("error", "Invalid username or password");
            return "Login";
        }
    }

    // Handles GET request for the Home page, redirects to Login page if user is not logged in.
    @GetMapping("/home")
    public String home(HttpSession session, Model model) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser == null || loggedInUser.getRole().equals("Admin")) {
            return "redirect:/login";
        }
        model.addAttribute("loggedInUser", loggedInUser);
        model.addAttribute("accounts", accountService.findByUser(loggedInUser));
        return "Home";
    }

    @GetMapping("/admin/home")
    public String adminHome(HttpSession session, Model model) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser == null || !loggedInUser.getRole().equals("Admin")) {
            return "redirect:/login";
        }
        model.addAttribute("loggedInUser", loggedInUser);
        return "AdminHome";
    }

    // Handles logout request, ending the session and redirecting to login page.
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}
