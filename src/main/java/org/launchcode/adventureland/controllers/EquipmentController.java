package org.launchcode.adventureland.controllers;

import org.launchcode.adventureland.models.Equipment;
import org.launchcode.adventureland.models.data.EquipmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("equipment")
public class EquipmentController {
    private static final List<Equipment> equipment = new ArrayList<>();
    @Autowired
    EquipmentRepository equipmentRepository;

    @GetMapping("")
    public String displayEquipment(Model model){

        model.addAttribute("EquipmentList", equipmentRepository.findAll());
        model.addAttribute("equipment", equipment);
        return "equipment/view";

    }
}