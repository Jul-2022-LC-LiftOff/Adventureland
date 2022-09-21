package org.launchcode.adventureland.models;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

//import static org.launchcode.adventureland.controllers.EquipmentController.equipment;


@Entity
public class Reserved extends AbstractEntity {


//    @NotNull
    private String resStatus;

    private double total;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "reserved", cascade=CascadeType.ALL)
    private List<Reservation> reservations = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public Reserved() {
    }

    public Reserved(String resStatus, double total, User user) {
       super();
        this.resStatus = resStatus;
        this.total = total;
        this.user = user;

    }

    public String getResStatus() {
        return resStatus;
    }

    public void setResStatus(String resStatus) {
        this.resStatus = resStatus;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }


    public List<Reservation> getReservations() {
        return reservations;
    }


    public void setReservations(List<Reservation> reservations) {
        this.reservations = reservations;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }



    //////////////////////// https://stackoverflow.com/questions/46637167/spring-boot-data-rest-jpa-manytoone-not-populating-the-foreignkey-column-in-db///////////////////////////////////
    //TODO: create as a list
    public void addReservation(Reservation reservation) {
        this.reservations.add(reservation);
//        reservation.setReserved(this);
    }
//    public User updateReservationUserId(User user) {
//        List<Reservation> reservations = user.getReservations();
//        for (Reservation reservation : reservations ) {
//            reservation.setUser(user);
//        }
//        return user;
//    }
////////////////////////////////////////////////////////////////////////

    @Override
    public String toString() {
        return "Reserved{" +
                "resStatus='" + resStatus + '\'' +
                "total='" + total + '\'' +
                '}';
    }

}


