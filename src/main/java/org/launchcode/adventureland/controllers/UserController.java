package org.launchcode.adventureland.controllers;


import org.launchcode.adventureland.dto.UserRegistrationDto;
import org.launchcode.adventureland.models.Reservation;
import org.launchcode.adventureland.models.User;
import org.launchcode.adventureland.models.data.UserRepository;
import org.launchcode.adventureland.service.UserService;
import org.launchcode.adventureland.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;




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
            //if user is not authenticated/logged in, return register form.
        }

        return "redirect:/";
        //otherwise, redirect to home page.
    }


    @PostMapping("register")
    public String processRegistrationForm(@ModelAttribute("user") UserRegistrationDto registrationDto, Errors errors) throws ParseException {
        if (userRepository.findByEmail(registrationDto.getEmail()) != null) {
            errors.rejectValue("email", "email.duplicate", "Email is already registered.");
            return "user/register";
            //if there is already a user with that email address in the repository, then show error message and return registration form.
        }
        String userBirthday = registrationDto.getBirthdate();
        LocalDate userBirthdayDate = LocalDate.parse(userBirthday);
        LocalDate now = LocalDate.now();
        if ((userBirthdayDate.getYear() > now.minusYears(18).getYear()) || (userBirthdayDate.getYear() == now.minusYears(18).getYear() && userBirthdayDate.getDayOfYear() > now.getDayOfYear())) {
            errors.rejectValue("birthdate", "birthdate.year.greater", "Must be at least 18.");
            return "user/register";
            //if the user's birth year is greater than the current one minus 18 years OR the user's birth year is equal to the current year minus 18 BUT still not 18, then reject value.
        }

        userService.save(registrationDto);
        return "user/register_success";
        //otherwise, return registration success page.
    }

    @GetMapping("login")
    public String displayLoginPage(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            //model.addAttribute("user", new User());
            return "user/login";
            //if user is not authenticated/logged in, return login form.
        }

        return "redirect:/";
        //otherwise, return home page.
    }

    @GetMapping("account")
    public String displayAccount(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            return "user/login";
        }
        String email = authentication.getName();
        User user = userRepository.findByEmail(email);
        String firstName = user.getFirstName();
        List<Reservation> reservations = user.getReservations();
        model.addAttribute("title", firstName + "'s Account");
        model.addAttribute("reservations", reservations);
        model.addAttribute("user", user);


        return "loggedInUser/account";
    }

    @GetMapping("account/edit-name")
    public String displayEditNameForm(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            return "redirect:/login";
            //if user is not authenticated/logged in, return login form.
        }

        String email = authentication.getName();
        User user = userRepository.findByEmail(email);
        String firstName = user.getFirstName();
        List<Reservation> reservations = user.getReservations();
        model.addAttribute("title", firstName + "'s Account");
        model.addAttribute("reservations", reservations);
        model.addAttribute("user", user);
        return "loggedInUser/edit-name";

    }

    @PostMapping("account/edit-name")
    public String processEditNameForm(String name) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        User user = userRepository.findByEmail(email);
        String[] nameArray = name.split(" ");
        user.setFirstName(nameArray[0]);
        user.setLastName(nameArray[nameArray.length - 1]);
        userRepository.flush();


        return "redirect:/account";
    }

}