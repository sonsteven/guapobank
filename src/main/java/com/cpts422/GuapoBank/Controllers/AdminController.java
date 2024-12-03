package com.cpts422.GuapoBank.Controllers;

import com.cpts422.GuapoBank.Entities.Account;
import com.cpts422.GuapoBank.Services.AccountService;
import com.cpts422.GuapoBank.Entities.User;
import com.cpts422.GuapoBank.Services.InterestService;
import com.cpts422.GuapoBank.Services.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

@Controller
public class AdminController {

    @Autowired
    private UserService userService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private InterestService interestService;

    @GetMapping("/admin/home")
    public String adminHome(HttpSession session, Model model) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser == null || !loggedInUser.getRole().equals("Admin")) {
            return "redirect:/login";
        }

        Iterable<User> users = userService.findAll();
        model.addAttribute("users", users);
        model.addAttribute("loggedInUser", loggedInUser);
        model.addAttribute("currentDate", LocalDate.now());

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

    // Get request handler for freezing an account.
    @GetMapping("/admin/account/{id}/freeze")
    public String freezeAccount(@PathVariable Long id, HttpSession session) {
        // Check that User is logged in and has Admin role.
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser == null || !loggedInUser.getRole().equals("Admin")) {
            return "redirect:/login";
        }

        // Search for the account with given ID.
        Optional<Account> accountOpt = accountService.findById(id);

        // Check if the account exists, if it does retrieve and freeze the account.
        if (accountOpt.isPresent()) {
            Account account = accountOpt.get();
            account.setFrozen(true);
            accountService.save(account);
            return "redirect:/admin/user/{id}/accounts";
        }

        // Account does not exist, redirect to home.
        return "redirect:/admin/user/{id}/accounts";
    }

    // Get request handler for unfreezing an account.
    @GetMapping("/admin/account/{id}/unfreeze")
    public String unfreezeAccount(@PathVariable Long id, HttpSession session) {
        // Check that User is logged in and has Admin role.
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser == null || !loggedInUser.getRole().equals("Admin")) {
            return "redirect:/login";
        }

        // Search for the account with given ID.
        Optional<Account> accountOpt = accountService.findById(id);

        // Check if the account exists, if it does retrieve and unfreeze the account.
        if (accountOpt.isPresent()) {
            Account account = accountOpt.get();
            account.setFrozen(false);
            accountService.save(account);
            return "redirect:/admin/user/{id}/accounts";
        }

        // Account does not exist, redirect to home.
        return "redirect:/admin/home";
    }
}
