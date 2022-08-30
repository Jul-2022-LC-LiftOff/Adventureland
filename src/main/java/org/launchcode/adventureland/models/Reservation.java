package org.launchcode.adventureland.models;


import org.springframework.ui.Model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import java.util.ArrayList;
import java.util.List;


@Entity
public class Reservation extends AbstractEntity {


    @NotNull
    private String equipmentName;


    private String dateReserved;

    @NotNull
    private int equipmentQuantity;

    private double unitPrice;

    private double total;


    @ManyToMany
    @JoinColumn(name = "reservation_id")
    private final List<Equipment> equipment = new ArrayList<>();

    @ManyToOne
    private User user;

    public Reservation(String equipmentName, String dateReserved, int equipmentQuantity, double unitPrice, double total, User user) {
        this.equipmentName = equipmentName;
        this.dateReserved = dateReserved;
        this.equipmentQuantity = equipmentQuantity;
        this.unitPrice = unitPrice;
        this.total = total;
        this.user = user;
    }

    public Reservation() {

    }

    public Reservation(String equipmentName, double unitPrice) {
        super();
        this.equipmentName = equipmentName;
        this.unitPrice = unitPrice;
    }

    public Reservation(String equipmentName, String dateReserved, int equipmentQuantity, double unitPrice, double total) {
        super();
        this.equipmentName = equipmentName;
        this.dateReserved = dateReserved;
        this.equipmentQuantity = equipmentQuantity;
        this.unitPrice = unitPrice;
        this.total = total;
    }


    public String getEquipmentName() {
        return equipmentName;
    }

    public void setEquipmentName(String equipmentName) {
        this.equipmentName = equipmentName;
    }

    public String getDateReserved() {

        return dateReserved;
    }

    public void setDateReserved(String dateReserved) {
        this.dateReserved = dateReserved;
    }

    public int getEquipmentQuantity() {
        return equipmentQuantity;
    }

    public void setEquipmentQuantity(int equipmentQuantity) {
        this.equipmentQuantity = equipmentQuantity;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public double getTotal() {
        return total;
    }

    public List<Equipment> getEquipment() {
        return equipment;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    //    public Number calculateTotal(id) {
//
//        Where equals ID
//
//            double total = unitPrice * equipmentQuantity
//
//        return total;
//    }

    public void setTotal(double total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "equipmentName='" + equipmentName + '\'' +
                ", dateReserved=" + dateReserved +
                ", equipmentQuantity=" + equipmentQuantity +
                ", total=" + total +
                ", equipment=" + equipment +
                ", user=" + user +
                '}';
    }
}