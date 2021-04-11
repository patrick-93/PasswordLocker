package com.example.passwordlocker.controllers.users;

import com.example.passwordlocker.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
}
