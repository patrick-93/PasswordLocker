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
import org.springframework.core.env.Environment;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.sql.Timestamp;
import java.util.*;

@Controller
@RequestMapping("/accounts")
public class ReadAccount {

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

    @GetMapping(value="")
    public ModelAndView showAll() {
        ModelAndView mav = new ModelAndView("accounts/accounts");
        mav.addObject("accounts", accountRepository.findAll());
        return mav;
    }

    @GetMapping("/details")
    public String accountDetail(@RequestParam(name = "id") long id, Model model) {
        Account account = accountRepository.findById(id).orElse(new Account());

        // Check if we have an actual account object, if not show an error page
        if (account.getUsername() != null) {
            model.addAttribute("account", account);
            return "accounts/account-details";
        } else {
            String error = "Unable to find an account with that ID";
            model.addAttribute("error", error);
            return "errors/account-error";
        }
    }

    @GetMapping("/showPassword")
    public String detailWithPassword(@RequestParam(name = "id") long id, Model model) {
        // Get current logged in user
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = userRepository.getUserByUsername(auth.getName());

        // Check if user is an auditor, show unauthorized page if so
        if (currentUser.getRoles().equals("AUDITOR")) {
            return "errors/unauthorized-access";
        }

        Account account = accountRepository.findById(id).orElse(new Account());

        // Check if we have an actual account object, if not show an error page
        if (account.getUsername() != null) {
            // Valid account so log a message that a user viewed it

            logRepository.save(
                    new Log(currentUser.getUsername() + " viewed the password of " + account.getUsername())
            );

            // Decrypt the password so it displays
            String encryptedPassword = account.getPassword();
            String decryptedPassword = PasswordConfig.decryptString(key, salt, encryptedPassword);
            account.setPassword(decryptedPassword);

            model.addAttribute("account", account);
            return "accounts/account-details-password";
        } else {
            String error = "Unable to find an account with that ID";
            model.addAttribute("error", error);
            return "errors/account-error";
        }
    }
}

