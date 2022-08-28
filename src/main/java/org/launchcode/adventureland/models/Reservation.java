package org.launchcode.adventureland.models;


import org.springframework.ui.Model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;


@Entity
public class Reservation extends AbstractEntity {


    @NotNull
    private String equipmentName;

    @Transient
    private SimpleDateFormat dateReserved;

//    @NotNull
    private int equipmentQuantity;

    private BigDecimal unitPrice;

    private BigDecimal total;


    @ManyToMany
    @JoinColumn(name = "reservation_id")
    private final List<Equipment> equipment = new ArrayList<>();

    @ManyToOne
    private User user;

    public Reservation(String equipmentName, String dateReserved, int equipmentQuantity, BigDecimal unitPrice, BigDecimal total, User user) {
        this.equipmentName = equipmentName;
        this.dateReserved = new SimpleDateFormat(dateReserved);
        this.equipmentQuantity = equipmentQuantity;
        this.unitPrice = unitPrice;
        this.total = total;
        this.user = user;
    }

    public Reservation() {

    }

    public Reservation(Model model) {
        super();
        this.equipmentName = (String) model.getAttribute("equipmentName");
        this.dateReserved = (SimpleDateFormat) model.getAttribute("dateReserved");
        this.equipmentQuantity = (int) model.getAttribute("equipmentQuantity");
        this.unitPrice = (BigDecimal) model.getAttribute("unitPrice");
        this.total = (BigDecimal) model.getAttribute("total");
        this.user = (User) model.getAttribute("user");
    }

    public Reservation(String equipmentName, SimpleDateFormat dateReserved, int equipmentQuantity, BigDecimal unitPrice, BigDecimal total) {
        super();
        this.equipmentName = equipmentName;
        this.dateReserved = dateReserved;
        this.equipmentQuantity = equipmentQuantity;
        this.unitPrice = unitPrice;
        this.total = total;
    }

    public Reservation(String equipmentName, BigDecimal price) {
        super();
        this.equipmentName = equipmentName;
        this.unitPrice = price;
    }


    public String getEquipmentName() {
        return equipmentName;
    }

    public void setEquipmentName(String equipmentName) {
        this.equipmentName = equipmentName;
    }

    public SimpleDateFormat getDateReserved() {

        return dateReserved;
    }

    public void setDateReserved(SimpleDateFormat dateReserved) {
        this.dateReserved = dateReserved;
    }

    public int getEquipmentQuantity() {
        return equipmentQuantity;
    }

    public void setEquipmentQuantity(int equipmentQuantity) {
        this.equipmentQuantity = equipmentQuantity;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public BigDecimal getTotal() {
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


    public void setTotal(BigDecimal total) {
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
