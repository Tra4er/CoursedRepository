package com.coursed.controllers;

import com.coursed.dto.AccountRegistrationFormData;
import com.coursed.entities.Account;
import com.coursed.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


/**
 * Created by Hexray on 16.10.2016.
 */

@Controller
public class AccountController {

    @Autowired
    AccountRepository accountRepository;

    // Login form
    @RequestMapping("/login")
    public String login() {
        return "login";
    }

    // Login form with error
    @RequestMapping("/login-error")
    public String loginError(Model model) {
        model.addAttribute("loginError", true);
        return "login";
    }

    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public String getRegistration(Model model) {
        return "registration";
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public String postRegistration(AccountRegistrationFormData form, Model model) {
        if (form.getPassword().equals(form.getConfirmPassword())) {

            Account account = new Account();
            account.setEmail(form.getEmail());
            account.setPassword(form.getPassword());

            accountRepository.save(account);
        } else
            model.addAttribute("message", "Пароли не совпадают");

        return "registration";
    }

    @RequestMapping(value = "/account/{id}")
    public String viewAccount(@PathVariable("id") Long id, Model model) {

        if (accountRepository.exists(id)) {
            Account account = accountRepository.findOne(id);
            model.addAttribute("accounts", account);
        } else
            model.addAttribute("accounts", "Учетной записи с таким id не существует.");

        return "accounts";
    }

    @RequestMapping(value = "/accounts")
    public String viewAllAccounts(Model model) {

        model.addAttribute("accounts", accountRepository.findAll());

        return "accounts";
    }

}
