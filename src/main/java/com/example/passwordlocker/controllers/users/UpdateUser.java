package com.example.passwordlocker.controllers.users;

import com.example.passwordlocker.models.Log;
import com.example.passwordlocker.models.User;
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
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UpdateUser {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private LogRepository logRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;


    // Get mapping for the regular user edit, admin edit will be separate mapping
    @GetMapping("/users/edit")
    public String editUserGet(@RequestParam(name = "id", required = true) long id, Model model) {
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
    // Post mapping for regular user edit
    @PostMapping("/users/edit")
    public String editUserPost(@ModelAttribute("user") User user, BindingResult bindingResult) {
        // Get current logged in user
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = userRepository.getUserByUsername(auth.getName());

        // Make sure currentUser is the same one trying to be modified
        if (!currentUser.getUsername().equals(user.getUsername())) {
            return "errors/unauthorized-access";
        }

        // currentUser and user are the same so get the original
        User originalUser = userRepository.getUserByUsername(user.getUsername());

        // Now check to make sure properties aren't null and modify
        // the originalUser with fields from the edit-user form
        if (!user.getFirstName().isEmpty()) {
            originalUser.setFirstName(user.getFirstName());
        }
        if (!user.getLastName().isEmpty()) {
            originalUser.setLastName(user.getLastName());
        }
        if (!user.getPassword().isEmpty()) {
            originalUser.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        originalUser.setLastUpdatedById(user.getUserId());
        originalUser.setLastUpdatedBy(user.getUsername());

        // Now save the originalUser and log a message
        userRepository.save(originalUser);
        logRepository.save(new Log(currentUser.getUsername() + " modified " + originalUser.getUsername()));

        return "redirect:/";
    }

    // Get mapping the admin to edit a user
    @GetMapping("/users/admin/edit")
    public String editUserAdminGet(@RequestParam(name = "id", required = true) long id, Model model) {
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
        if (currentUser.getRoles().equals("ADMIN")) {
            // User has permissions to edit so show edit page
            model.addAttribute("user", user);
            return "users/admin-edit-user";
        }
        // Current user is not the user to be edited
        return "errors/unauthorized-access";
    }

    @PostMapping("/users/admin/edit")
    public String editUserAdminPost(@ModelAttribute("user") User user, BindingResult bindingResult) {
        // Get current logged in user
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = userRepository.getUserByUsername(auth.getName());

        // Make sure currentUser is the same one trying to be modified
        if (!currentUser.getRoles().equals("ADMIN")) {
            return "errors/unauthorized-access";
        }

        // currentUser is an admin so get the original user
        User originalUser = userRepository.findById(user.getUserId()).orElse(new User());

        // Now check to make sure properties aren't null and modify
        // the originalUser with fields from the edit-user form
        if (!user.getFirstName().isEmpty()) {
            originalUser.setFirstName(user.getFirstName());
        }
        if (!user.getLastName().isEmpty()) {
            originalUser.setLastName(user.getLastName());
        }
        if (!user.getPassword().isEmpty()) {
            originalUser.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        if (!user.getUsername().isEmpty()) {
            originalUser.setUsername(user.getUsername());
        }

        originalUser.setLastUpdatedById(user.getUserId());
        originalUser.setLastUpdatedBy(user.getUsername());

        // Now save the originalUser and log a message
        userRepository.save(originalUser);
        logRepository.save(new Log(currentUser.getUsername() + " modified " + originalUser.getUsername()));

        return "redirect:/";
    }
}
