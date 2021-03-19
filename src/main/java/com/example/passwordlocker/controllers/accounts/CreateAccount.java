package com.example.passwordlocker.controllers.accounts;

import com.example.passwordlocker.repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value={"/accounts/create", "/accounts/create/"})
public class CreateAccount {

    @Autowired
    private AccountRepository repository;

    @GetMapping(value={"/", ""})
    public String createIndex() {
        return "create-account";
    }

    // @PostMapping(value={"/", ""})

}
