package org.launchcode.adventureland.controllers;

import org.launchcode.adventureland.models.*;
import org.launchcode.adventureland.models.data.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import java.lang.reflect.Array;
import java.util.*;

import static org.springframework.data.util.CastUtils.cast;
import static org.thymeleaf.util.StringUtils.length;


//import org.launchcode.adventureland.persistent.models.Reservation;
//import org.launchcode.adventureland.persistent.models.data.reservationRepository;


@Controller
@RequestMapping("reservation")
public class ReservationController {

    private static List<Reservation> reservation = new ArrayList<>();
    private static HashMap<String, Integer> reservedDatesList = new HashMap<>();
    Reserved reserved = new Reserved();
    Integer reservedId;
    Integer reservationId;
    private int totalReservedQty = 0;
    Equipment equipment = new Equipment();

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private EquipmentRepository equipmentRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ReservedRepository reservedRepository;


    @GetMapping("")
    public String displayReservationList(Model model) {
        model.addAttribute("title", "Cart");
        model.addAttribute("ReservationList", reservationRepository.findAll());
        model.addAttribute("reservation", reservation);


        if (UserData.isUserNotLoggedIn()) {
            return "reservation/cartView";
        }

        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(email);
        model.addAttribute("user", user);
        return "reservation/cartView";
    }

    // **SEE BELOW**
    // Below handles the retrieval of "a piece of equipment" TO RESERVE.
    @GetMapping("resFormView/{ViewRequest}")
    public String displayAddReservationForm(Model model, @PathVariable String ViewRequest) {

        Reservation reservation;
        var val = ViewRequest.trim().split("=");
        String viewRequestFrom = val[0];
        Integer viewRequestID = Integer.valueOf(val[1]);
        reservedDatesList.clear();
        model.addAttribute("title", "Reserve Equipment");

        if (viewRequestFrom.equals("ReservationId")) {
//            Below creates an object using an existing id (i.e. this creates a reservation object using and equipment ID)
            Reservation existingReservation = new Reservation();
            Optional<Reservation> optReservation = reservationRepository.findById(viewRequestID);
            if (optReservation.isPresent()) {
                // Below is made using the global variable declared at the top
                existingReservation = optReservation.get();
            }

            Optional<Equipment> optEquipment = equipmentRepository.findById(existingReservation.getEquipmentId());
            if (optEquipment.isPresent()) {
                equipment = optEquipment.get();
                existingReservation.setEquipment(equipment);
            }

            if (!reservationRepository.findAllByEquipmentId(equipment.getId()).toString().isEmpty()) {
                ArrayList<Object> listOfReservations = reservationRepository.findAllByEquipmentId(equipment.getId());
                for (var res : listOfReservations) {
                    String datesReserved = (String) Array.get(res, 1);
                    Integer qty = (Integer) Array.get(res, 3);
                    reservedDatesList.put(datesReserved, qty);
                    totalReservedQty = totalReservedQty + qty;
                }
                existingReservation.setReservationStatus("Pending-Edit");
                model.addAttribute("reservedDatesList", reservedDatesList);
            } else {
                //ToDo: else with there are no reservation. Not sure if the below is the correct approach null
                model.addAttribute("reservedDatesList", null);
            }
//                reserved.setTotal((reserved.getTotal()) - (existingReservation.getUnitPrice()) * (existingReservation.getEquipmentQuantity()));
            // this creates duplicate reservation.
//                reservedRepository.save(reserved);

            reservation = existingReservation;
            reservationId = reservation.getId();

            //reservationRepository.updateReservationStatus(reservation.getReservationStatus(), reservation.getId());

        } else if (viewRequestFrom.equals("EquipmentId")) {
//        Below creates an object using an existing id (i.e. this creates an equipment object using and equipment ID)
            Reservation newReservation = new Reservation();
            Optional<Equipment> optEquipment = equipmentRepository.findById(viewRequestID);
            if (optEquipment.isPresent()) {
                equipment = optEquipment.get();
                newReservation.setEquipment(equipment);
            }

            if (!reservationRepository.findAllByEquipmentId(viewRequestID).toString().isEmpty()) {
                ArrayList<Object> listOfReservations = reservationRepository.findAllByEquipmentId(viewRequestID);
                for (var res : listOfReservations) {
                    String datesReserved = (String) Array.get(res, 1);
                    Integer qty = (Integer) Array.get(res, 3);
                    reservedDatesList.put(datesReserved, qty);
                    totalReservedQty = totalReservedQty + qty;
                }
                newReservation.setReservationStatus("Pending-Checkout");
                model.addAttribute("reservedDatesList", reservedDatesList);
            } else {
                //ToDo: else with there are no reservation. Not sure if the below is the correct approach null
                model.addAttribute("reservedDatesList", null);
            }
            newReservation.setEquipmentQuantity(1);
            reservation = newReservation;

        } else {
            return "redirect:../";
        }

        model.addAttribute("totalReservedQty", totalReservedQty);
        model.addAttribute("equipment", equipment);
        model.addAttribute("reservation", reservation);

        return "reservation/resFormView";
    }


    @PostMapping("cartView")
    public String processAddReservationForm(@ModelAttribute Reservation newReservation, Errors errors, @ModelAttribute Equipment equipment, Model model) {
        if (errors.hasErrors()) {
            return "reservation/resFormView";
        } else {
            model.addAttribute("title", "My Cart");
            if (newReservation.getReservationStatus() == null) {
                newReservation.setReservationStatus("Pending-Checkout");
            }
            String currentStatus = newReservation.getReservationStatus();
            if (currentStatus.equals("Pending-Checkout")) {
                if (reserved.getResStatus() == null) {
                    reserved = new Reserved();
                    //TODO: how to get the logged in user ID
                    //            reserved.setUserId(user.getId());
                } else if (reserved.getResStatus() != null) {
                    if (reservedId != null) {
                        Optional<Reserved> optReserved = reservedRepository.findById(reservedId);
                        if (optReserved.isPresent()) {
                            Reserved checkReserved = optReserved.get();
                            if (checkReserved.getResStatus().equals("Active")) {
                                reserved = null;
                                reserved = new Reserved();
                            }
                        }
                    } else {
                        reserved = null;
                        reserved = new Reserved();
                    }
                }

                newReservation.setTotal((newReservation.getUnitPrice()) * (newReservation.getEquipmentQuantity()));
                reserved.setTotal((reserved.getTotal()) + (newReservation.getTotal()));
                reserved.setResStatus("Pending-Checkout");


//           The below line uses the reservation object and sets the association with the reserved object
//           by pulling in the reserved_id into reservation (this allows the reserved_id column to be
//           populated in the reservation table).
                reservedRepository.save(reserved);
                reservedId = reserved.getId();

                newReservation.setReserved(reserved);
                reservationRepository.save(newReservation);
                reservationId = newReservation.getId();



            } else if (currentStatus.equals("Pending-Edit")) {
//                    Optional<Reserved> optReserved = reservedRepository.findById(newReservation.getReservedId());
//                    if (optReserved.isPresent()) {
//                        reserved = optReserved.get();
//                        newReservation.setReserved(reserved);
//                    }
                // get orginal reservation qty and price so that the original balance can be
                // deducted from the reserved total.
                Optional<Reservation> optReservation = reservationRepository.findById(reservationId);
                Reservation orginalReservation = null;
                if (optReservation.isPresent()) {
                    // Below is made using the global variable declared at the top
                    orginalReservation = optReservation.get();
                }
                reserved.setTotal((reserved.getTotal()) - (orginalReservation.getUnitPrice()) * (orginalReservation.getEquipmentQuantity()));

                //Update reservation total and reserved total with new values after being edited
                newReservation.setReservationStatus("Pending-Checkout");
                newReservation.setTotal((newReservation.getUnitPrice()) * (newReservation.getEquipmentQuantity()));
                reserved.setTotal((reserved.getTotal()) + (newReservation.getTotal()));
                reserved.setResStatus("Pending-Checkout");


//           The below line uses the reservation object and sets the association with the reserved object
//           by pulling in the reserved_id into reservation (this allows the reserved_id column to be
//           populated in the reservation table).
                reservedRepository.save(reserved);
                reservedId = reserved.getId();

                newReservation.setReserved(reserved);
//                reservationRepository.save(newReservation);
                updateReservation(newReservation.getReservationStatus(), newReservation.getEquipmentQuantity(), newReservation.getDateReserved(), reservationId);

            }

//________________________________________________________________________________________________________________
//            Build object/models for CartView, containing list of reservations matching Reserved ID

            List<Reservation> reservations = reservationRepository.findAllByReservedId(reservedId);
            reserved.setReservations(reservations);
//___________________________________________________________________________________________________________________
//            Prepare HTML for the models the webpage will use to display info

            model.addAttribute("reserved", reserved);
            model.addAttribute("reservationList", reservations);

//            return "redirect:";
            return "reservation/cartView";
        }


    }

    private void updateReservation(String reservationStatus, Integer qty, String dateReserved, Integer reservationId) {
        Optional<Reservation> optReservation = reservationRepository.findById(reservationId);
        if (optReservation.isPresent()) {
            Reservation updateReservation = optReservation.get();
            updateReservation.setReservationStatus(reservationStatus);
            updateReservation.setEquipmentQuantity(qty);
            updateReservation.setDateReserved(dateReserved);
            reservationRepository.save(updateReservation);
        }
    }


    @GetMapping("cartView/{cartViewRequest}")
    public String displayCart(Model model, @PathVariable String cartViewRequest) {

        var val = cartViewRequest.trim().split("=");
        String cartViewRequestFrom = val[0];
        Integer cartViewRequestID = Integer.valueOf(val[1]);

        model.addAttribute("title", "My Cart");
        if (reservedId != null) {
            Optional<Reserved> optReserved = reservedRepository.findById(reservedId);
            if (optReserved.isPresent()) {
                Reserved pendingReserved = optReserved.get();
                model.addAttribute("reserved", pendingReserved);
            }
            if (!reservationRepository.findAllByReservedId(reservedId).toString().isEmpty()) {
                List<Reservation> pendingReservations = reservationRepository.findAllByReservedId(reservedId);
                model.addAttribute("reservationList", pendingReservations);
            }
            return "reservation/cartView";
        } else {

            Reserved pendingReserved = new Reserved();
            pendingReserved.setTotal(0);
            reservedRepository.save(pendingReserved);


            model.addAttribute("reserved", pendingReserved);
            return "reservation/cartView";
        }
    }

    @GetMapping("resConfirmView")
    public String displayConfirmation(Model model) {

        model.addAttribute("title", "Reservation Confirmed");

        Optional<Reserved> optReserved = reservedRepository.findById(reservedId);
        if (optReserved.isPresent()) {
            Reserved confirmedReserved = optReserved.get();
            confirmedReserved.setResStatus("Active");
            reservedRepository.save(confirmedReserved);
            model.addAttribute("reserved", confirmedReserved);
        }
        if (!reservationRepository.findAllByReservedId(reservedId).toString().isEmpty()) {
            List<Reservation> confirmedReservations = reservationRepository.findAllByReservedId(reservedId);
            for (Reservation confirmedRes : confirmedReservations) {
                confirmedRes.setReservationStatus("Booked");
                reservationRepository.save(confirmedRes);
            }
            model.addAttribute("reservationList", confirmedReservations);
        }

        reservedDatesList.clear();
//            reserved = null;
        reservedId = null;
        reservationId = null;
        totalReservedQty = 0;
//            equipment = null;

        return "reservation/resConfirmView";
    }
}