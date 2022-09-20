package org.launchcode.adventureland.controllers;

import org.launchcode.adventureland.models.Equipment;
import org.launchcode.adventureland.models.Reservation;
import org.launchcode.adventureland.models.data.EquipmentRepository;
import org.launchcode.adventureland.models.data.ReservationRepository;
import org.launchcode.adventureland.models.data.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.persistence.Entity;
import javax.validation.Valid;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("reservation")
public class ReservationController {

    private static final List<Reservation> reservation = new ArrayList<>();

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private EquipmentRepository equipmentRepository;

    @Autowired
    UserRepository userRepository;


    @GetMapping("")
    public String displayReservationList(Model model) {
        model.addAttribute("title", "Cart");
        model.addAttribute("reservation", reservation);
        return "reservation/cartView";
    }

    // **SEE BELOW**
    // Below handles the retrieval of "a piece of equipment" TO RESERVE.
    @GetMapping("resFormView/{equipmentId}")
    public String displayAddReservationForm(Model model, @PathVariable  Integer equipmentId) {
        model.addAttribute("title", "Reserve Equipment");

        Optional<Equipment> optEquipment = equipmentRepository.findById(equipmentId);
        if (optEquipment.isPresent()) {
            Equipment equipment = (Equipment) optEquipment.get();

            model.addAttribute("equipment", equipment);
            model.addAttribute("reservation", new Reservation(equipment.getEquipmentName(), equipment.getPrice()));

            return "reservation/resFormView";
        } else {
            return "redirect:../";
        }
    }

    @GetMapping("cartView")
    public String displayCart(Model model /*@PathVariable int userId*/) {
        model.addAttribute("ReservationList", reservationRepository.findAll());
        model.addAttribute("reservation", reservation);

        return"reservation/cartView";
    }

    @PostMapping("cartView")
    public String processAddReservationForm(@ModelAttribute Reservation newReservation, Errors errors, Model model) {
        if (errors.hasErrors()) {
            return "reservation/resFormView";
        } else {
            reservationRepository.save(newReservation);
            model.addAttribute("ReservationList", reservationRepository.findAll());
            model.addAttribute("reservation", reservation);
            return "reservation/cartView";
        }


    }

    @GetMapping("resConfirmView")
    public String displayConfirmation(Model model) {
        model.addAttribute("title", "Your reservation has been confirmed!");
        model.addAttribute("ReservationList", reservationRepository.findAll());
        model.addAttribute("reservation", reservation);
        return"reservation/resConfirmView";
    }



}
