package org.launchcode.adventureland.models;

import javax.persistence.Entity;

@Entity
public class Equipment extends AbstractEntity{

    private String category;

    private String equipmentName;

    private String manufacturer;

    private int quantity;

    private int price;

    public Equipment(){}

    public Equipment(String category, String equipmentName, String manufacturer, int quantity, int price) {
        this.category = category;
        this.equipmentName = equipmentName;
        this.manufacturer = manufacturer;
        this.quantity = quantity;
        this.price = price;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
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
