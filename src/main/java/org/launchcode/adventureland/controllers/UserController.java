package org.launchcode.adventureland.controllers;

import org.launchcode.adventureland.dto.UserRegistrationDto;
import org.launchcode.adventureland.models.User;
import org.launchcode.adventureland.models.data.UserRepository;
import org.launchcode.adventureland.service.UserService;
import org.launchcode.adventureland.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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

    private UserService userService;


    public UserController(UserService userService) {
        super();
        this.userService = userService;
    }

    @ModelAttribute("user")
    public UserRegistrationDto userRegistrationDto() {
        return new UserRegistrationDto();
    }


    @GetMapping("register")
    public String displayRegistrationForm() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            return "user/register";
        }

        return "redirect:/";
    }

    @PostMapping("register")
    public String processRegistrationForm(@ModelAttribute("user") UserRegistrationDto registrationDto) {
        if (userRepository.findByEmail(registrationDto.getEmail()) != null) {
            return "user/already_registered";
        }
        userService.save(registrationDto);
        return "user/register_success";
    }

    @GetMapping("login")
    public String displayLoginPage(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            model.addAttribute("user", new User());
            return "user/login";
        }

        return "redirect:/";
        //return "user/login";
    }


//    @PostMapping("login")
//    public String processLoginForm(@Valid @ModelAttribute("user") UserRegistrationDto registrationDto) {
//        User user = userRepository.findByEmail(registrationDto.getEmail());
//        if (user != null) {
//            userService.loadUserByUsername(registrationDto.getEmail());
//            return "redirect:/";
//        }
//
//        return "redirect:/login";
//
//    }
    //    @PostMapping("login")
//    public String processLoginPage(String email, String password, Errors errors, Model model) {
//        UserDetails foundUser = userService.loadUserByUsername(email);
//        if (foundUser == null) {
//            errors.rejectValue("email", "email.invalid", "Invalid email address");
//            return "login";
//        } else if (errors.hasErrors() || (!foundUser.getPassword().equals(password))) {
//            errors.rejectValue("password", "password.invalid", "Invalid password");
//            //add errors, or no found user
//                return "login";
//            } else {
//               return "redirect:/";
//            }
//    }
}