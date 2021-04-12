package com.example.passwordlocker.controllers.users;

import com.example.passwordlocker.models.Account;
import com.example.passwordlocker.models.User;
import com.example.passwordlocker.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
        model.addAttribute("users", userRepository.findAll());
        return "users/users";
    }

    @GetMapping("/details")
    public String getUserDetails(@RequestParam(name="id") long id, Model model) {
        User user = userRepository.findById(id).orElse(new User());
        if (user.getUsername() == null) {
            model.addAttribute("userNotFound", "Unable to find a user with ID: " + id);
            return "users/user-details";
        }
        model.addAttribute("user", user);
        return "users/user-details";
    }
}
