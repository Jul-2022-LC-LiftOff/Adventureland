package org.launchcode.adventureland.controllers;

import org.launchcode.adventureland.models.User;
import org.launchcode.adventureland.models.data.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("register")
    public String displayRegistrationForm(Model model) {
        model.addAttribute(new User());
        return "register";
    }

    @PostMapping("register")
    public String processRegistrationForm(@ModelAttribute @Valid User user,
                                          Errors errors, String verify) {
        if (errors.hasErrors() || (!user.getPassword().equals(verify))) {
            return "register";
        }

        userRepository.save(user);
        return "redirect:/";
    }

    @GetMapping("login")
    public String displayLoginPage(Model model) {
        model.addAttribute("user", new User());
        return "login";
    }

    @PostMapping("login")
    public String processLoginPage(@ModelAttribute User user,String email, String password, Errors errors, Model model) {
        User foundUser = userRepository.findByEmail(email);
        if (foundUser == null) {
            errors.rejectValue("email", "email.invalid", "Invalid email address");
            return "login";
        } else if (errors.hasErrors() || (!foundUser.getPassword().equals(password))) {
            errors.rejectValue("password", "password.invalid", "Invalid password");
            //add errors, or no found user
                return "login";
            } else {
               return "redirect:/";
            }
    }
}