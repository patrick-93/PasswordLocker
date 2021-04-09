package com.example.passwordlocker.controllers;

import com.example.passwordlocker.models.User;
import com.example.passwordlocker.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class IndexController {

    @Autowired
    private UserRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping(value={"/", "/index"})
    private String index() {
        return "index";
    }

    @GetMapping(value="/create-first-user")
    private String createFirstUser(Model model) {
        boolean check = checkIfAdminExists();
        if (!check) {
            model.addAttribute("user", new User());
            return "create-first-user";
        }
        return "index";
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
        user.setRole("USER");
        user.setEnabled(true);
        repository.save(user);
        return "index";
    }

    private boolean checkIfAdminExists() {
        User admin = repository.getUserByUsername("admin");
        if (admin != null) { return true; }
        return false;
    }
}
