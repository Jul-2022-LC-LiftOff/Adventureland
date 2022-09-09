package org.launchcode.adventureland.controllers;

import org.launchcode.adventureland.models.User;
import org.launchcode.adventureland.models.UserData;
import org.launchcode.adventureland.models.data.CategoryRepository;
import org.launchcode.adventureland.models.data.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController  {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("")
    public String index(Model model) {
        if (UserData.isUserNotLoggedIn()) {
            return "index";
        }

        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(email);
        model.addAttribute("user", user);
        return "index";
    }


}
