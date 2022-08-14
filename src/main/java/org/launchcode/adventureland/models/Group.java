package org.launchcode.adventureland.models;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
//@Table(name = "group")
public class Group extends AbstractEntity{

    private String description;


    @OneToMany
    @JoinColumn(name = "group_id")
    private final List<Inventory> inventory = new ArrayList<>();

    public Group(){}

    public Group(String aDescription){
        super();
        this.description = aDescription;
    }
}