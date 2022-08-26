package org.launchcode.adventureland.models;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Category extends AbstractEntity{

    private String name;
    private String description;


    @OneToMany
    @JoinColumn(name = "equipment_id")
    private final List<Equipment> equipment = new ArrayList<>();

    public Category(){}

    public Category(String aName, String aDescription){
        super();
        this.name = aName;
        this.description = aDescription;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Equipment> getEquipment() {
        return equipment;
    }

    @Override
    public String toString() {
        return name;
    }
}