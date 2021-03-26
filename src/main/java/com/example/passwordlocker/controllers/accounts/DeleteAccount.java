package com.example.passwordlocker.controllers.accounts;

import com.example.passwordlocker.models.Account;
import com.example.passwordlocker.repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class DeleteAccount {

    @Autowired
    private AccountRepository repository;

    @GetMapping("/accounts/delete")
    public String deleteAccount(@RequestParam(name="id", required = true) long id) {
        Account account = repository.findById(id).orElse(new Account());
        if (repository.existsById(id)) {
            repository.deleteById(id);
        }
        return "redirect:/accounts";
    }
}
