package org.launchcode.adventureland.controllers;

import org.launchcode.adventureland.models.User;
import org.launchcode.adventureland.models.UserData;
import org.launchcode.adventureland.models.data.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class AboutController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("about")
    public String displayAbout(Model model){
        if (UserData.isUserNotLoggedIn()) {
            return "about/index";
        }

        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(email);
        model.addAttribute("user", user);
        return "about/index";

    }

}