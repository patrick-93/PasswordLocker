package com.example.passwordlocker.controllers.accounts;

import com.example.passwordlocker.models.Account;
import com.example.passwordlocker.repositories.AccountRepository;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import javax.validation.Valid;

@Controller
@RequestMapping(value={"/accounts/create", "/accounts/create/"})
public class CreateAccount {

    @Autowired
    private AccountRepository repository;

    @GetMapping(value={"/", ""})
    public String createAccountGet(Model model) {
        model.addAttribute("account", new Account());
        return "accounts/create-account-new";
    }

    @PostMapping(value={"/", ""})
    public String createAccountPost(@ModelAttribute("account") Account account, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "accounts/create-account";
        }
        repository.save(account);
        return "redirect:/";
    }

}
