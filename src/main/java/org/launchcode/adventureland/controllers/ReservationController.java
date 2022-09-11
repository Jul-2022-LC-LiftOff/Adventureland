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

import javax.validation.constraints.NotBlank;
import java.lang.reflect.Array;
import java.util.*;
;import static org.springframework.data.util.CastUtils.cast;


//import org.launchcode.adventureland.persistent.models.Reservation;
//import org.launchcode.adventureland.persistent.models.data.reservationRepository;


@Controller
@RequestMapping("reservation")
public class ReservationController {

    private static List<Reservation> reservation = new ArrayList<>();
    private static HashMap<String, Integer> reservedDatesList = new HashMap<>();
    private int totalReservedQty =0 ;

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private EquipmentRepository equipmentRepository;

    @Autowired
    UserRepository userRepository;


    @GetMapping("")
    public String displayReservationList(Model model) {
        model.addAttribute("title", "Cart");
        model.addAttribute("ReservationList",reservationRepository.findAll());
        model.addAttribute("reservation", reservation);

        return "reservation/cartView";
    }

    // **SEE BELOW**
    // Below handles the retrieval of "a piece of equipment" TO RESERVE.
    @GetMapping("resFormView/{equipmentId}")
    public String displayAddReservationForm(Model model, @PathVariable  Integer equipmentId) {
        model.addAttribute("title", "Reserve Equipment");
        //        int equipmentId = 1577;
        Optional<Equipment> optEquipment = equipmentRepository.findById(equipmentId);
        if (optEquipment.isPresent()) {
            Equipment equipment = (Equipment) optEquipment.get();

            String equipName = equipment.getEquipmentName();
            double equipPrice = equipment.getPrice();

            if (!reservationRepository.findAllByEquipmentId(equipName).toString().isEmpty()) {

                ArrayList<Object> listOfReservations = reservationRepository.findAllByEquipmentId(equipName);
                for (var variable : listOfReservations) {
                    String datesReserved = (String) Array.get(variable, 1);
                    Integer qty = (Integer) Array.get(variable, 3);
                    reservedDatesList.put(datesReserved, qty);
                }

            }else {
                model.addAttribute("reservedDatesList", null);
            }

            model.addAttribute("reservedDatesList", reservedDatesList);
            model.addAttribute("totalReservedQty", totalReservedQty);
            model.addAttribute("equipment", equipment);
            model.addAttribute("reservation", new Reservation(equipment));
            return "reservation/resFormView";
        } else {
            return "redirect:../";
        }
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


    @GetMapping("cartView")
    public String displayCart(Model model /*@PathVariable int userId*/) {
        model.addAttribute("title", "Cart");
        model.addAttribute("ReservationList", reservationRepository.findAll());
        model.addAttribute("reservation", reservation);
        //        return "reservation/cart";
        return"reservation/cartView";
    }

//    @PostMapping("resConfirmView")
//    public String processCart(Model model) {
//        model.addAttribute("title", "Cart");
////      reservation.add(equipemntQuantity);
////    reservation.add(dateReserved);
//        return"reservation/resConfirmView";
//    }

    @GetMapping("resConfirmView")
    public String displayConfirmation(Model model) {
        model.addAttribute("title", "Your reservation has been confirmed!");
        model.addAttribute("ReservationList", reservationRepository.findAll());
        model.addAttribute("reservation", reservation);
        return"reservation/resConfirmView";
    }

    //    @GetMapping("view/{userId}")

    @GetMapping("view")
    public String displayViewReservation(Model model) {
        model.addAttribute("title", "My Reservations");
        return "reservation/view";
    }


}