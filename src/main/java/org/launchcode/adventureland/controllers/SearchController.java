package org.launchcode.adventureland.controllers;

import org.launchcode.adventureland.models.CatData;
import org.launchcode.adventureland.models.Equipment;
import org.launchcode.adventureland.models.data.EquipmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;

@Controller
@RequestMapping("search")
public class SearchController {

    @Autowired
    private EquipmentRepository equipmentRepository;

    static HashMap<String, String> columnChoices = new HashMap<>();

    public SearchController(){
        columnChoices.put("name", "Name");
        columnChoices.put("manufacturer", "Manufacturer");
        columnChoices.put("category", "Category");
    }

    @RequestMapping("")
    public String search(Model model){
        model.addAttribute("columns", columnChoices);
        return "search";
    }

    @PostMapping("results")
    public String displaySearchResults(Model model, @RequestParam String searchType, @RequestParam String searchTerm){
        Iterable<Equipment> equipment;

        equipment = CatData.findByColumnAndValue(searchType, searchTerm, equipmentRepository.findAll());

        model.addAttribute("columns", columnChoices);
        model.addAttribute("title", "Equipment matching " + columnChoices.get(searchType) + "with " +searchTerm);
        model.addAttribute("equipment", equipment);
        return "search";
    }
}
