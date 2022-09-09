package org.launchcode.adventureland.controllers;


import org.launchcode.adventureland.models.*;
import org.launchcode.adventureland.models.Category;
import org.launchcode.adventureland.models.data.EquipmentRepository;
import org.launchcode.adventureland.models.data.CategoryRepository;
import org.launchcode.adventureland.models.data.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("equipment")
public class EquipmentController {

    @Autowired
    EquipmentRepository equipmentRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("")
    public String displayEquipment(Model model){

        model.addAttribute("EquipmentList", equipmentRepository.findAll());
        if (UserData.isUserNotLoggedIn()) {
            return "equipment/view";
        }

        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(email);
        model.addAttribute("user", user);
        return "equipment/view";

    }
    @GetMapping("add")
    public String displayEquipmentForm(Model model) {
        model.addAttribute("title", "Add Equipment to Inventory");
        model.addAttribute("categories", categoryRepository.findAll());
        model.addAttribute("newEquipment", new Equipment());

        return "equipment/add";
    }


    @PostMapping("add")
    public String processEquipmentForm(@ModelAttribute @Valid Equipment newEquipment) {

        equipmentRepository.save(newEquipment);

        return "redirect:";

    }
}