package com.example.passwordlocker.controllers.accounts;

import com.example.passwordlocker.config.PasswordConfig;
import com.example.passwordlocker.models.Account;
import com.example.passwordlocker.models.Log;
import com.example.passwordlocker.models.User;
import com.example.passwordlocker.repositories.AccountRepository;
import com.example.passwordlocker.repositories.LogRepository;
import com.example.passwordlocker.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;

@Controller
public class UpdateAccount {

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

    @GetMapping(value="/accounts/edit")
    public String editAccountGet(@RequestParam(name = "id", required = true) long id, Model model) {
        Account account = accountRepository.findById(id).orElse(new Account());
        try {
            System.out.println("\n\nFound account created by" + account.getCreatedBy());
        } catch (Exception e) {
            System.out.println("\n\nDid not find a real account, using an empty one");
        }
        model.addAttribute("account", account);
        model.addAttribute("pageTitle", "Edit Account");
        return "accounts/edit-account";
    }

    @PostMapping(value="/accounts/edit")
    public String editAccountPost(@ModelAttribute("account") Account account, BindingResult bindingResult) {
        System.out.println("\n\nEdit account post created by " + account.getCreatedBy());

        // Get current logged in user to save the new account
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.getUserByUsername(auth.getName());

        // Check for errors
        if (bindingResult.hasErrors()) {
            return "accounts/edit-account";
        }

        // Encrypt the password and set the updated by fields
        account.setPassword(PasswordConfig.encryptString(key, salt, account.getPassword()));
        account.setLastUpdatedBy(user.getUsername());
        account.setLastUpdatedById(user.getUserId());

        accountRepository.save(account);

        // Now build a log message and save
        Log message = new Log();
        message.setContent(user.getUsername() + " modified " + account.getUsername());
        logRepository.save(message);

        return "redirect:/accounts";
    }
}
