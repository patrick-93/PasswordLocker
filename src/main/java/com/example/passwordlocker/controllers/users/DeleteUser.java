package com.example.passwordlocker.controllers.users;

import com.example.passwordlocker.models.Log;
import com.example.passwordlocker.models.User;
import com.example.passwordlocker.repositories.LogRepository;
import com.example.passwordlocker.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class DeleteUser {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private LogRepository logRepository;

    @GetMapping("/users/delete")
    public String deleteUser(@RequestParam(name = "id", required = true) long id) {
        User user = userRepository.findById(id).orElse(new User());

        // Check if user valid, if not return to /users
        if (user.getUsername() == null) {
            return "redirect:/users";
        }
        // User is valid so get the current user
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = userRepository.getUserByUsername(auth.getName());

        // Check if user has role of ADMIN
        if (!currentUser.getRoles().equals("ADMIN")) {
            return "errors/unauthorized-access";
        }

        // User has a role of ADMIN so delete and log
        userRepository.deleteById(id);
        logRepository.save(new Log(currentUser.getUsername() + " deleted " + user.getUsername()));

        return "redirect:/users";

    }
}
