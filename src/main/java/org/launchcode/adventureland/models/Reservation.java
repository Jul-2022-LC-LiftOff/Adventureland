package org.launchcode.adventureland.models;


import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

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


//
//    @ManyToOne
//    @JoinColumn(name = "equipment_id")
//    private final List<Equipment> equipment = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "equipment_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Equipment equipment;

    @ManyToOne
    private User user;


    public Reservation(String dateReserved, int equipmentQuantity, double total, User user, Equipment equipment) {
        this.equipmentName = equipment.getEquipmentName();
        this.dateReserved = dateReserved;
        this.equipmentQuantity = equipmentQuantity;
        this.unitPrice = equipment.getPrice();
        this.total = total;
        this.user = user;
        this.equipment = equipment;
    }

    public Reservation() {

    }

    public Reservation(Equipment equipment) {
        super();
        this.equipmentName = equipment.getEquipmentName();
        this.unitPrice = equipment.getPrice();
        this.equipment = equipment;
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
        return (List<Equipment>) equipment;
    }
    public void setEquipment(Equipment equipment) {
        this.equipment = equipment;
    }
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

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

