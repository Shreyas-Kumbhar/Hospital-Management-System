package com.hospital.hospitalMngtSys.controller;

import com.hospital.hospitalMngtSys.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    /* ── Login ── */

    @GetMapping("/login")
    public String loginPage(
            @RequestParam(value = "error",  required = false) String error,
            @RequestParam(value = "logout", required = false) String logout,
            Model model) {

        if (error  != null) model.addAttribute("errorMsg",  "Invalid username or password.");
        if (logout != null) model.addAttribute("successMsg","You have been logged out.");
        return "login";
    }

    /* ── Register ── */

    @GetMapping("/register")
    public String registerPage() {
        return "register";
    }

    @PostMapping("/register")
    public String register(
            @RequestParam String username,
            @RequestParam String password,
            @RequestParam String confirmPassword,
            RedirectAttributes redirectAttributes) {

        // Basic validations
        if (username == null || username.isBlank()) {
            redirectAttributes.addFlashAttribute("errorMsg", "Username cannot be empty.");
            return "redirect:/register";
        }
        if (password == null || password.length() < 6) {
            redirectAttributes.addFlashAttribute("errorMsg", "Password must be at least 6 characters.");
            return "redirect:/register";
        }
        if (!password.equals(confirmPassword)) {
            redirectAttributes.addFlashAttribute("errorMsg", "Passwords do not match.");
            return "redirect:/register";
        }
        if (userService.existsByUsername(username)) {
            redirectAttributes.addFlashAttribute("errorMsg", "Username '" + username + "' is already taken.");
            return "redirect:/register";
        }

        userService.register(username, password);
        redirectAttributes.addFlashAttribute("successMsg", "Account created! You can now log in.");
        return "redirect:/login";
    }
}
