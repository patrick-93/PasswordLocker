package com.example.passwordlocker.controllers;

import com.example.passwordlocker.models.User;
import com.example.passwordlocker.repositories.LogRepository;
import com.example.passwordlocker.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/logs")
public class LogController {

    @Autowired
    private LogRepository logRepository;
    @Autowired
    private UserRepository userRepository;

    @GetMapping("")
    public String showLogs(Model model) {
        // Get current logged in user
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = userRepository.getUserByUsername(auth.getName());

        // Check if current user is a regular user, return unauthorized page if true
        if (currentUser.getRoles().equals("USER")) {
            return "errors/unauthorized-access";
        }
        // User has access so show logs
        model.addAttribute("logs", logRepository.findAll());
        return "logs/logs";
    }
}