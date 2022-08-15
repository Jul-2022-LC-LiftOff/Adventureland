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
    public String processRegistrationForm(String verify, @ModelAttribute @Valid User user,
                                          Errors errors, Model model) {
        if (!errors.hasErrors() && (user.getPassword().equals(verify))) {
            userRepository.save(user);
            return "redirect:";
        }

        return "register";
    }
}