package org.launchcode.adventureland.controllers;

import org.launchcode.adventureland.models.data.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "categories")
public class GroupController{

    @Autowired
    private GroupRepository groupRepository;

    @RequestMapping("")
    public String group(Model model) {
        model.addAttribute("group", groupRepository.findAll());
        return "group/index";
    }

}