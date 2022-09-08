package org.launchcode.adventureland.controllers;

import org.launchcode.adventureland.models.CatData;
import org.launchcode.adventureland.models.Equipment;
import org.launchcode.adventureland.models.data.CategoryRepository;
import org.launchcode.adventureland.models.Category;
import org.launchcode.adventureland.models.data.EquipmentRepository;
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
    private EquipmentRepository equipmentRepository;

    @GetMapping("")
    public String index(Model model) {
        model.addAttribute("title", "All Categories");
        model.addAttribute("categories", categoryRepository.findAll());
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            return "categories/index";
        }
        return "loggedInUser/categories";

    }

    // this will need items displayed below

    @GetMapping("/{categoryId}")
    public String displayViewCategory(Model model, @PathVariable  Integer categoryId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Optional optCategory = categoryRepository.findById(categoryId);
        if (optCategory.isPresent() && (authentication == null || authentication instanceof AnonymousAuthenticationToken)) {
            Category category = (Category) optCategory.get();
            Iterable<Equipment> equipmentInCategory;

            String value = category.toString();

            equipmentInCategory = CatData.findByValue(value, equipmentRepository.findAll());
            model.addAttribute("category", category);
            model.addAttribute("title", "Equipment in " + value);
            model.addAttribute("equipments", equipmentInCategory);

            return "catView";
        } else if (optCategory.isPresent() && (authentication != null && !(authentication instanceof AnonymousAuthenticationToken))) {
            Category category = (Category) optCategory.get();
            Iterable<Equipment> equipmentInCategory;

            String value = category.toString();

            equipmentInCategory = CatData.findByValue(value, equipmentRepository.findAll());
            model.addAttribute("category", category);
            model.addAttribute("title", "Equipment in " + value);
            model.addAttribute("equipments", equipmentInCategory);

            return "loggedInUser/catView";
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