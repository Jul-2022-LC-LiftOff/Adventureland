package org.launchcode.adventureland.models;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.math.BigDecimal;

@Entity
public class Equipment extends AbstractEntity{

    @ManyToOne
    private Category category;

    private String equipmentName;

    private String manufacturer;

    private int quantity;

    private double price;

    public Equipment(){}

    public Equipment(Category category, String equipmentName, String manufacturer, int quantity, double price) {
        super();
        this.category = category;
        this.equipmentName = equipmentName;
        this.manufacturer = manufacturer;
        this.quantity = quantity;
        this.price = price;
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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
