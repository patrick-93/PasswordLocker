package com.example.passwordlocker.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class LogoutController {

    @PostMapping
    public String logout() {
        return "redirect:/";
    }
}
