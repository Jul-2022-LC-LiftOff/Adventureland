package org.launchcode.adventureland.controllers;

import org.launchcode.adventureland.models.CatData;
import org.launchcode.adventureland.models.Equipment;
import org.launchcode.adventureland.models.User;
import org.launchcode.adventureland.models.UserData;
import org.launchcode.adventureland.models.data.EquipmentRepository;
import org.launchcode.adventureland.models.data.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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

    @Autowired
    private UserRepository userRepository;

    static HashMap<String, String> searchChoices = new HashMap<>();

    public SearchController(){
        searchChoices.put("all", "All");
        searchChoices.put("name", "Name");
        searchChoices.put("manufacturer", "Manufacturer");
        searchChoices.put("category", "Category");
    }

    @RequestMapping("")
    public String search(Model model){
        model.addAttribute("searchTypes", searchChoices);

        if (UserData.isUserNotLoggedIn()) {
            return "search";
        }
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(email);
        model.addAttribute("user", user);
        return "search";
    }

    @PostMapping("results")
    public String displaySearchResults(Model model, @RequestParam String searchType, @RequestParam String searchTerm){
        Iterable<Equipment> equipment;

        equipment = CatData.findByColumnAndValue(searchType, searchTerm, equipmentRepository.findAll());

        model.addAttribute("searchTypes", searchChoices);
        model.addAttribute("title", "Equipment containing: " + searchTerm);
        model.addAttribute("subtitle", "Search by: " + searchChoices.get(searchType));
        model.addAttribute("equipments", equipment);

        if (UserData.isUserNotLoggedIn()) {
            return "searchResults";
        }
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(email);
        model.addAttribute("user", user);
        return "searchResults";
    }
}
