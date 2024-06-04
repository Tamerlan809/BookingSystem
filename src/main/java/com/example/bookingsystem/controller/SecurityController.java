package com.example.bookingsystem.controller;

import com.example.bookingsystem.services.impl.PasswordResetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
public class SecurityController {

    @Autowired
    private PasswordResetService passwordResetService;

    @GetMapping("/login")
    public String login(@RequestParam(value = "error", required = false) String error,
                        @RequestParam(value = "reset", required = false, defaultValue = "false") boolean reset,
                        @RequestParam(value = "resetEmailSent", required = false) String resetEmailSent,
                        Model model) {
        if (error != null) {
            model.addAttribute("loginError", "Fel användarnamn eller lösenord.");
        }
        if (reset) {
            model.addAttribute("reset", true);
        }
        if (resetEmailSent != null) {
            model.addAttribute("resetEmailSent", resetEmailSent);
        }
        return "login";
    }

    @GetMapping("/resetpass")
    public String resetPassword(@RequestParam String email, RedirectAttributes redirectAttributes) {
        boolean resetInitiated = passwordResetService.initiatePasswordReset(email);
        if (resetInitiated) {
            redirectAttributes.addFlashAttribute("resetEmailSent", "Instruktioner för att byta lösenord har skickats till " + email);
        } else {
            redirectAttributes.addFlashAttribute("userNotExist", "Användaren finns inte i databasen.");
        }
        return "redirect:/login";
    }

    @GetMapping("/reset")
    public String showResetPasswordPage(@RequestParam String token, Model model) {
        model.addAttribute("token", token);
        return "reset-password";
    }

    @PostMapping("/process-reset")
    public String processReset(@RequestParam String token, @RequestParam String password, RedirectAttributes redirectAttributes) {
        boolean success = passwordResetService.resetPassword(token, password);
        if (success) {
            redirectAttributes.addFlashAttribute("message", "Ditt lösenord har uppdaterats.");
        } else {
            redirectAttributes.addFlashAttribute("error", "Begäran att byta lösenord är förfallen.");
        }
        return "redirect:/login";
    }
}
