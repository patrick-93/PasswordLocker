package com.example.passwordlocker.controllers.accounts;

import com.example.passwordlocker.models.Account;
import com.example.passwordlocker.models.Log;
import com.example.passwordlocker.models.User;
import com.example.passwordlocker.repositories.AccountRepository;
import com.example.passwordlocker.repositories.LogRepository;
import com.example.passwordlocker.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class DeleteAccount {

    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private LogRepository logRepository;

    @GetMapping("/accounts/delete")
    public String deleteAccount(@RequestParam(name="id", required = true) long id) {
        Account account = accountRepository.findById(id).orElse(new Account());

        if (accountRepository.existsById(id)) {
            accountRepository.deleteById(id);

            // Now log a message that a user deleted an account
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            User user = userRepository.getUserByUsername(auth.getName());
            logRepository.save(new Log(user.getUsername() + " deleted " + account.getUsername()));
        }
        return "redirect:/accounts";
    }
}
