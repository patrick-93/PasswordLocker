package com.example.passwordlocker.controllers;

import com.example.passwordlocker.models.User;
import com.example.passwordlocker.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
@RequestMapping("/")
public class IndexController {

    @Autowired
    private UserRepository repository;

    @GetMapping(value={"/", "/index"})
    private String index() {
        Optional<User> admin = Optional.ofNullable(repository.getUserByUsername("admin"));
        if (admin.isPresent()) {
            return "index";
        }
        else {
            return "redirect:/create-first-user";
        }
    }
}
