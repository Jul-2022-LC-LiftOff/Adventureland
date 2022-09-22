package org.launchcode.adventureland.models;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Equipment extends AbstractEntity{

    @ManyToOne
    private Category category;

    private String equipmentName;

    private String manufacturer;

    private int quantity;

    private int price;

    public Equipment(){}

    public Equipment(Category aCategory, String anEquipmentName, String aManufacturer, int aQuantity, int aPrice) {
        super();
        this.category = aCategory;
        this.equipmentName = anEquipmentName;
        this.manufacturer = aManufacturer;
        this.quantity = aQuantity;
        this.price = aPrice;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getEquipmentName() {
        return equipmentName;
    }

    public void setEquipmentName(String equipmentName) {
        this.equipmentName = equipmentName;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
