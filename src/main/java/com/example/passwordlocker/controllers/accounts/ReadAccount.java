package com.example.passwordlocker.controllers.accounts;

import com.example.passwordlocker.models.Account;
import com.example.passwordlocker.repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
    private AccountRepository repository;

    @GetMapping(value="")
    public ModelAndView showAll() {
        ModelAndView mav = new ModelAndView("accounts/accounts");
        mav.addObject("accounts", repository.findAll());
        return mav;
    }

    @GetMapping("/details")
    public String accountDetail(@RequestParam(name = "id", required = true) long id, Model model) {
        Account account = repository.findById(id).orElse(new Account());
        model.addAttribute("account", account);
        return "accounts/account-details";
    }

}

