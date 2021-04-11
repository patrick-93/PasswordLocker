package com.example.passwordlocker.controllers.accounts;

import com.example.passwordlocker.config.PasswordConfig;
import com.example.passwordlocker.models.Account;
import com.example.passwordlocker.models.Log;
import com.example.passwordlocker.models.User;
import com.example.passwordlocker.repositories.AccountRepository;
import com.example.passwordlocker.repositories.LogRepository;
import com.example.passwordlocker.repositories.UserRepository;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import javax.validation.Valid;

@Controller
@RequestMapping(value={"/accounts/create", "/accounts/create/"})
public class CreateAccount {

    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private LogRepository logRepository;

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

        // Get current logged in user to save the new account
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.getUserByUsername(auth.getName());

        // Check for errors
        if (bindingResult.hasErrors()) {
            return "accounts/create-account";
        }

        // Encrypt the password and set the created by fields
        account.setPassword(PasswordConfig.encryptString(key, salt, account.getPassword()));
        account.setCreatedBy(user.getUsername());
        account.setCreatedById(user.getUserId());

        accountRepository.save(account);

        // Now build a log message and save
        Log message = new Log();
        message.setContent(user.getUsername() + " created " + account.getUsername());
        logRepository.save(message);

        return "redirect:/accounts";
    }

}
