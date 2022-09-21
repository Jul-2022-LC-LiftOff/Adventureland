package org.launchcode.adventureland.models;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashMap;
//import static org.launchcode.adventureland.controllers.EquipmentController.equipment;

@Entity
public class Reservation extends AbstractEntity {

    @NotNull
    private String equipmentName;

    @NotNull(message = "Please select a date.")
    private String dateReserved;

    @NotNull
    private int equipmentQuantity;

    private double unitPrice;

    private double total;

    private String reservationStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "equipment_id")
    private Equipment equipment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reserved_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Reserved reserved;


    public Reservation() {

    }

    public Reservation(String dateReserved, int equipmentQuantity, double total, String reservationStatus, Equipment equipment, Reserved reserved) {
        this.equipmentName = equipment.getEquipmentName();
        this.dateReserved = dateReserved;
        this.equipmentQuantity = equipmentQuantity;
        this.unitPrice = equipment.getPrice();
        this.total = total;
        this.reservationStatus = reservationStatus;
        this.equipment = equipment;
        this.reserved = reserved;
    }

    public Reservation(Equipment equipment) {
        super();
        this.equipmentName = equipment.getEquipmentName();
        this.unitPrice = equipment.getPrice();
        this.equipment = equipment;
//        this.setEquipment(equipment);
    }

    public Reservation(String equipmentName, String dateReserved, int equipmentQuantity, double unitPrice, double total, String reservationStatus, Equipment equipment, Reserved reserved) {
        super();
        this.equipmentName = equipmentName;
        this.dateReserved = dateReserved;
        this.equipmentQuantity = equipmentQuantity;
        this.unitPrice = unitPrice;
        this.total = total;
        this.reservationStatus = reservationStatus;
        this.equipment = equipment;
        this.reserved = reserved;
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

    public void setTotal(double total) {
        this.total = total;
    }

    public String getReservationStatus() {
        return reservationStatus;
    }

    public void setReservationStatus(String reservationStatus) {
        this.reservationStatus = reservationStatus;
    }

    public Equipment getEquipment() {
        return equipment;
    }

    public void setEquipment(Equipment equipment) {
        this.equipmentName = equipment.getEquipmentName();
        this.unitPrice = equipment.getPrice();
        this.equipment = equipment;
    }

    public Integer getEquipmentId() {
        return this.equipment.getId();
    }

    public Reserved getReserved() {
        return reserved;
    }

    public void setReserved(Reserved reserved) {
        this.reserved = reserved;
    }

    public Integer getReservedId(){
        return this.reserved.getId();
    }



    @Override
    public String toString() {
        return "Reservation{" +
                "equipmentName='" + equipmentName + '\'' +
                ", dateReserved=" + dateReserved +
                ", equipmentQuantity=" + equipmentQuantity +
                ", total=" + total +
                ", reservationStatus=" + reservationStatus +
                ", equipment=" + equipment +
                ", reserved=" + reserved +
                '}';
    }

}
