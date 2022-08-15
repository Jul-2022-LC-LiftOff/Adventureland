package org.launchcode.adventureland.controllers;

import org.launchcode.adventureland.models.data.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(value = "categories")
public class CategoryController{

    @Autowired
    private CategoryRepository categoryRepository;

    @GetMapping("")
    public String group(Model model) {
        model.addAttribute("title", "All Categories");
        model.addAttribute("categories", categoryRepository.findAll());
        return "categories/index";
    }

}