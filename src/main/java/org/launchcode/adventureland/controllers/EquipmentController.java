package org.launchcode.adventureland.controllers;


import org.launchcode.adventureland.models.*;
import org.launchcode.adventureland.models.data.EquipmentRepository;
import org.launchcode.adventureland.models.data.CategoryRepository;
import org.launchcode.adventureland.models.data.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequestMapping("equipment")
public class EquipmentController {

    @Autowired
    EquipmentRepository equipmentRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    private UserRepository userRepository;

    Integer equipmentId;

    @GetMapping("")
    public String displayEquipment(Model model){

        model.addAttribute("equipments", equipmentRepository.findAll());
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

    @GetMapping("edit/{equipmentId}")
    public String displayEditEquipmentForm(Model model, @PathVariable Integer equipmentId) {
        model.addAttribute("title", "Editing: ");
        Optional<Equipment> optEquipment = equipmentRepository.findById(equipmentId);
        if (optEquipment.isPresent()) {
            Equipment equipment = (Equipment) optEquipment.get();
            equipmentId = equipment.getId();
            model.addAttribute("categories", categoryRepository.findAll());
            model.addAttribute("equipment", equipment);

        }
        return "equipment/edit";

    }
    @PostMapping("edit/{id}")
    public String processEditEquipmentForm(@ModelAttribute Equipment updateEquipment, Model model, @PathVariable Integer id) {


        Optional<Equipment> optEquipment = equipmentRepository.findById(id);
        if (optEquipment.isPresent()) {
            Equipment equipment = (Equipment) optEquipment.get();
            equipment.setEquipmentName(updateEquipment.getEquipmentName());
            equipment.setCategory(updateEquipment.getCategory());
            equipment.setManufacturer(updateEquipment.getManufacturer());
            equipment.setQuantity(updateEquipment.getQuantity());
            equipment.setPrice(updateEquipment.getPrice());
            equipmentRepository.save(equipment);
        }

        return "redirect:/";

    }
}