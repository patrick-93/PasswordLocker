package com.example.passwordlocker.controllers.users;

import com.example.passwordlocker.models.Account;
import com.example.passwordlocker.models.User;
import com.example.passwordlocker.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/users")
public class ReadUser {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("")
    public String showAll(Model model) {
        // Get current user
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = userRepository.getUserByUsername(auth.getName());

        // Check if user has role of ADMIN or AUDITOR
        if (currentUser.getRoles().equals("ADMIN") || currentUser.getRoles().equals("AUDITOR")) {
            model.addAttribute("users", userRepository.findAll());
            return "users/users";
        }
        // User does not have access so show error page
        return "errors/unauthorized-access";
    }

    @GetMapping("/details")
    public String getUserDetails(@RequestParam(name="id") long id, Model model) {
        // Get current user
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = userRepository.getUserByUsername(auth.getName());

        // Check if user has role of ADMIN or AUDITOR
        if (currentUser.getRoles().equals("ADMIN") || currentUser.getRoles().equals("AUDITOR")) {

            // User is authorized so attempt to show details
            User user = userRepository.findById(id).orElse(new User());
            if (user.getUsername() == null) {

                // User ID is not valid, show an error
                model.addAttribute("userNotFound", "Unable to find a user with ID: " + id);
                return "users/user-details";
            }

            // User ID is valid so show details
            model.addAttribute("user", user);
            return "users/user-details";
        }
        // Current user does not have access, show error page
        return "errors/unauthorized-access";

    }
}
