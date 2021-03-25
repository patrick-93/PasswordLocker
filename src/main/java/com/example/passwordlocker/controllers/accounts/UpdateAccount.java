package com.example.passwordlocker.controllers.accounts;

import com.example.passwordlocker.models.Account;
import com.example.passwordlocker.repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UpdateAccount {

    @Autowired
    private AccountRepository repository;

    @GetMapping(value="/accounts/edit")
    public String editAccountGet(@RequestParam(name = "id", required = true) long id, Model model) {
        Account account = repository.findById(id).orElse(new Account());
        model.addAttribute("account", account);
        model.addAttribute("pageTitle", "Edit Account");
//        Account emptyAccount = new Account();
//        model.addAttribute("account", emptyAccount);
        return "accounts/create-account";
    }

    @PostMapping(value="/accounts/edit")
    public String editAccountPost(@ModelAttribute("account") Account account, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "/";
        }
        repository.save(account);
        return "redirect:/accounts";
    }
}
