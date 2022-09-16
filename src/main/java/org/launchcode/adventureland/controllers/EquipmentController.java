package org.launchcode.adventureland.controllers;


import org.launchcode.adventureland.models.Category;
import org.launchcode.adventureland.models.Equipment;
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

    @GetMapping("view")
    public String displayEquipment(Model model){

        model.addAttribute("EquipmentList", equipmentRepository.findAll());
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            return "equipment/view";
        }
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