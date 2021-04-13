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
        // Get current logged in user
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = userRepository.getUserByUsername(auth.getName());

        // Check if user has role of ADMIN, if not return the unauthorized page
        if (!currentUser.getRoles().equals("ADMIN")) {
            return "errors/unauthorized-access";
        }

        Account account = accountRepository.findById(id).orElse(new Account());

        if (accountRepository.existsById(id)) {
            accountRepository.deleteById(id);
            
            logRepository.save(new Log(currentUser.getUsername() + " deleted " + account.getUsername()));
        }
        return "redirect:/accounts";
    }
}
