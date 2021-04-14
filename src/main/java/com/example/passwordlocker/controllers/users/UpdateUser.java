package com.example.passwordlocker.controllers.users;

import com.example.passwordlocker.models.User;
import com.example.passwordlocker.repositories.LogRepository;
import com.example.passwordlocker.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UpdateUser {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private LogRepository logRepository;


    // Get mapping for the regular user edit, admin edit will be separate mapping
    @GetMapping("/users/edit")
    public String editUser(@RequestParam(name = "id", required = true) long id, Model model) {
        // find the user with the id param
        User user = userRepository.findById(id).orElse(new User());

        // Check if we found a real user
        if (user.getUsername() == null) {
            return "errors/user-error";
        }

        // Get current logged in user
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = userRepository.getUserByUsername(auth.getName());

        // Found a valid user so check if currentUser is either admin or the found user
        if (currentUser.getUsername().equals(user.getUsername())) {
            // User has permissions to edit so show edit page
            model.addAttribute("user", user);
            return "users/edit-user";
        }
        // Current user is not the user to be edited
        return "errors/unauthorized-access";
    }
}
