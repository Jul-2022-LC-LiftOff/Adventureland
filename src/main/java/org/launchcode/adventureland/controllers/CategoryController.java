package org.launchcode.adventureland.controllers;

import org.launchcode.adventureland.models.*;
import org.launchcode.adventureland.models.data.CategoryRepository;
import org.launchcode.adventureland.models.data.EquipmentRepository;
import org.launchcode.adventureland.models.data.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;
import java.util.Scanner;

@Controller
@RequestMapping(value = "categories")
public class CategoryController{

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EquipmentRepository equipmentRepository;

    @GetMapping("")
    public String index(Model model) {
        model.addAttribute("title", "All Categories");
        model.addAttribute("categories", categoryRepository.findAll());
        if (UserData.isUserNotLoggedIn()) {
            return "categories/index";
        }

        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(email);
        model.addAttribute("user", user);
        return "categories/index";

    }

    // this will need items displayed below

    @GetMapping("/{categoryId}")
    public String displayViewCategory(Model model, @PathVariable  Integer categoryId) {
        Optional optCategory = categoryRepository.findById(categoryId);
        Category category = (Category) optCategory.get();
        Iterable<Equipment> equipmentInCategory;

        String value = category.toString();

        equipmentInCategory = CatData.findByValue(value, equipmentRepository.findAll());
        model.addAttribute("category", category);
        model.addAttribute("title", "Equipment in " + value);
        model.addAttribute("equipments", equipmentInCategory);

        if (optCategory.isPresent() && (UserData.isUserNotLoggedIn())) {
            return "catView";
        } else if (optCategory.isPresent() && (!UserData.isUserNotLoggedIn())) {
            String email = SecurityContextHolder.getContext().getAuthentication().getName();
            User user = userRepository.findByEmail(email);
            model.addAttribute("user", user);
            return "catView";
        } else {
            return "redirect:../";
        }
    }
    @GetMapping("add")
    public String displayEquipmentForm(Model model) {
        model.addAttribute("title", "Add Category");
        model.addAttribute("newCategory", new Category());

        return "categories/add";
    }


    @PostMapping("add")
    public String processEquipmentForm(@ModelAttribute @Valid Category newCategory) {

        categoryRepository.save(newCategory);

        return "redirect:";

    }

}