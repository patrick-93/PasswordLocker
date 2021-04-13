package com.example.passwordlocker.controllers.users;

import com.example.passwordlocker.models.Log;
import com.example.passwordlocker.models.User;
import com.example.passwordlocker.repositories.AccountRepository;
import com.example.passwordlocker.repositories.LogRepository;
import com.example.passwordlocker.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CreateUser {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private LogRepository logRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/users/create")
    public String createUserGet(Model model) {
        // Get current logged in user
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = userRepository.getUserByUsername(auth.getName());

        // Check if user has role of ADMIN, if not return the unauthorized page
        if (!currentUser.getRoles().equals("ADMIN")) {
            return "errors/unauthorized-access";
        }

        model.addAttribute("user", new User());
        return "users/create-user";
    }

    @PostMapping("/users/create")
    public String createUserPost(@ModelAttribute("user") User user, BindingResult bindingResult, Model model) {
        // Get current logged in user
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = userRepository.getUserByUsername(auth.getName());

        // Check if user has role of ADMIN, if not return the unauthorized page
        if (!currentUser.getRoles().equals("ADMIN")) {
            return "errors/unauthorized-access";
        }

        // Check if username already exists
        String username = user.getUsername();
        User check = userRepository.getUserByUsername(username);
        if (check != null) {
            model.addAttribute("invalidUsername", "Username already exists");
            return "users/create-user";
        }

        // Check for errors
        if (bindingResult.hasErrors()) {
            return "users/create-user";
        }

        // Encrypt password, set the current user as creator, save
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setCreatedById(currentUser.getUserId());
        user.setCreatedBy(currentUser.getUsername());
        userRepository.save(user);

        // Generate a log message
        logRepository.save(
                new Log(currentUser.getUsername() + " created user " +
                        user.getUsername() + " with a role of " + user.getRoles())
        );

        return "index";
    }
}
