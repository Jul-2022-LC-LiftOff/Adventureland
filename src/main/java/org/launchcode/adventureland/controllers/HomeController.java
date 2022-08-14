package org.launchcode.adventureland.controllers;

import org.launchcode.adventureland.models.data.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class HomeController  {

    @Autowired
    private GroupRepository groupRepository;


}
