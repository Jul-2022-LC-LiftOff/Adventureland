package org.launchcode.adventureland.models;

//import javax.persistence.*;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Entity
public class User extends AbstractEntity {

    @NotBlank(message = "Must enter a first name.")
    private String firstName;
    @NotBlank(message = "Must enter a last name.")
    private String lastName;
    @NotBlank(message = "Must enter an email.")
    @Email(message = "Must be a valid email.")
    private String email;
    @NotBlank(message = "Must enter a password between 6 and 20 characters.")
    @Size(min = 6, max = 20)
    private String password;

    @NotNull(message = "Must enter a birthdate.")
    private String birthdate;

//    @OneToMany
//    private List<Reservation> reservations;

    public User(String firstName, String lastName, String email, String password, String birthdate) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.birthdate = birthdate;
    }

    public User() {
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    public String getPassword() {
        return password;
    }

    //    public List<Reservation> getReservations() {
//        return reservations;
//    }
//
//
}
