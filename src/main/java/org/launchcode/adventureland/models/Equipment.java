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

    private double price;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "equipment")
    private List<Reservation> reservations = new ArrayList<>();

    public Equipment(){}

    public Equipment(Category category, String equipmentName, String manufacturer, int quantity, double price, Reservation reservations) {
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

    public List<Reservation> getReservations() {
        return reservations;
    }

    @Override
    public String toString() {
        return "Equipment{" +
                "category='" + category + '\'' +
                ", equipmentName='" + equipmentName + '\'' +
                ", manufacturer='" + manufacturer + '\'' +
                ", quantity=" + quantity +
                ", price=" + price +
                '}';
    }
}
