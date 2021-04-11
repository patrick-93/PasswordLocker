package com.example.passwordlocker.controllers.users;

import com.example.passwordlocker.models.Log;
import com.example.passwordlocker.models.User;
import com.example.passwordlocker.repositories.LogRepository;
import com.example.passwordlocker.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class CreateFirstUser {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    LogRepository logRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping(value="/create-first-user")
    private String createFirstUser(Model model) {
        boolean check = checkIfAdminExists();
        if (!check) {
            model.addAttribute("user", new User());
            return "create-first-user";
        }
        return "redirect:/";
    }

    @PostMapping(value="/create-first-user")
    private String postCreateFirstUser(@ModelAttribute("user") User user, BindingResult bindingResult) {
        // Check if admin exists, if true do nothing and load index page
        boolean check = checkIfAdminExists();
        if (check) {
            return "redirect:/";
        }
        // admin doesn't exist so create from post data
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setUsername("admin");
        user.setRoles("ADMIN");
        user.setActive(true);
        userRepository.save(user);

        logRepository.save(new Log(user.getUsername() + " was created as a first user"));

        return "redirect:/";
    }

    private boolean checkIfAdminExists() {
        User admin = userRepository.getUserByUsername("admin");
        if (admin != null) { return true; }
        return false;
    }
}
