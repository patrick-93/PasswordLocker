package com.example.passwordlocker.controllers.accounts;

import com.example.passwordlocker.repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/accounts")
public class ReadAccount {

    @Autowired
    private AccountRepository repository;

    @GetMapping("/")
    public String showAll(Model model) {
        model.addAttribute("accounts", repository.findAll());
        return "index";
    }

}
