package org.launchcode.adventureland.controllers;

import org.launchcode.adventureland.models.data.EquipmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("equipment")
public class EquipmentController {

    @Autowired
    EquipmentRepository equipmentRepository;

    @GetMapping("")
    public String displayEquipment(Model model){

        model.addAttribute("EquipmentList", equipmentRepository.findAll());

        return "equipment/view";

    }
}