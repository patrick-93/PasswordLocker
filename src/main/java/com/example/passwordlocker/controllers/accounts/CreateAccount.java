package com.example.passwordlocker.controllers.accounts;

import com.example.passwordlocker.config.PasswordConfig;
import com.example.passwordlocker.models.Account;
import com.example.passwordlocker.repositories.AccountRepository;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

    @Value("${app.secret-key}")
    private String key;

    @Value("${app.secret-salt}")
    private String salt;

    @GetMapping(value={"/", ""})
    public String createAccountGet(Model model) {
        model.addAttribute("account", new Account());
        model.addAttribute("pageTitle", "Create Account");
        return "accounts/create-account";
    }

    @PostMapping(value={"/", ""})
    public String createAccountPost(@ModelAttribute("account") Account account, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "accounts/create-account";
        }
        String oldPassword = account.getPassword();
        String newPassword = PasswordConfig.encryptString(key, salt, oldPassword);
        account.setPassword(newPassword);

        repository.save(account);
        return "redirect:/accounts";
    }

}
