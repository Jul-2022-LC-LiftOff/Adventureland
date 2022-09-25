package org.launchcode.adventureland.controllers;

import org.launchcode.adventureland.models.Equipment;
import org.launchcode.adventureland.models.Reservation;
import org.launchcode.adventureland.models.Reserved;
import org.launchcode.adventureland.models.User;
import org.launchcode.adventureland.models.data.*;
import org.springframework.beans.factory.annotation.Autowired;
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

    private static HashMap<String, Integer> reservedDatesList = new HashMap<>();
    Reserved reserved = new Reserved();
    Reservation reservation = new Reservation();
    Equipment equipment = new Equipment();
    Integer reservedId;
    Integer reservationId;
    Integer equipmentId;
    private int totalReservedQty = 0;

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
        return "reservation/cartView";
    }

    @GetMapping("resFormView/{ViewRequest}")
    public String displayAddReservationForm(Model model, @PathVariable String ViewRequest) {
        // Initial Configuration
        Reservation reservation;
        var val = ViewRequest.trim().split("=");
        String viewRequestFrom = val[0];
        Integer viewRequestID = Integer.valueOf(val[1]);
        reservedDatesList.clear();
        model.addAttribute("title", "Reserve Equipment");
        // Logic
        if (viewRequestFrom.equals("ReservationId")) {
            // Used when editing a reservation
            Reservation existingReservation = getExistingReservationByID(viewRequestID, viewRequestFrom);
            existingReservation.setReservationStatus("Pending-Edit");
            equipmentId = existingReservation.getEquipmentId();
            equipment = getBuildEquipment(equipmentId);
            reservation = existingReservation;
        } else if (viewRequestFrom.equals("EquipmentId")) {
            // Used when creating a new reservation
            Reservation newReservation = new Reservation();
            newReservation.setReservationStatus("Pending-Checkout");
            newReservation.setEquipmentQuantity(1);
            equipment = getBuildEquipment(viewRequestID);
            equipmentId = equipment.getId();
            reservation = newReservation;
        } else {
            return "redirect:../";
        }


        //HTML Prep
        reservation.setEquipment(equipment);
        reservationId = reservation.getId();
        // Used for Calendar and jQuery
        reservedDatesList = getReservedDatesList(viewRequestFrom, viewRequestID);
        model.addAttribute("reservedDatesList", reservedDatesList);
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
            // Initial Configuration
            model.addAttribute("title", "My Cart");
            String currentStatus = newReservation.getReservationStatus();
            // Logic

            List<Reservation> reservations = null;
            Reservation originalReservation;
            if (currentStatus.equals("Pending-Checkout")) {
                // Used when making a new reservation
                if (reservedId != null) {
                    //existing reserved obj
                    Reserved checkReserved = getReservedByID(reservedId, currentStatus);
                    if (checkReserved.getResStatus() == "Active" || checkReserved.getResStatus() == "Archived") {
                        checkReserved = null;
                        reserved = new Reserved();
                    } else {
                        reserved = checkReserved;
                    }
                } else {
                    reserved = new Reserved();
                }
                newReservation.setTotal((newReservation.getUnitPrice()) * (newReservation.getEquipmentQuantity()));
                reserved.setTotal((reserved.getTotal()) + (newReservation.getTotal()));
                reserved.setResStatus("Pending-Checkout");
                reservedRepository.save(reserved);
                reservedId = reserved.getId();
                newReservation.setReserved(reserved);
                reservationRepository.save(newReservation);
                reservationId = newReservation.getId();
            } else if (currentStatus.equals("Pending-Edit")) {
                // Used when editing an existing reservation
                originalReservation = getExistingReservationByID(reservationId, currentStatus);
                reserved.setTotal((reserved.getTotal()) - (originalReservation.getUnitPrice()) * (originalReservation.getEquipmentQuantity()));
                newReservation.setReservationStatus("Pending-Checkout");
                newReservation.setTotal((newReservation.getUnitPrice()) * (newReservation.getEquipmentQuantity()));
                reserved.setTotal((reserved.getTotal()) + (newReservation.getTotal()));
                reserved.setResStatus("Pending-Checkout");
                reservedRepository.save(reserved);
                reservedId = reserved.getId();
                newReservation.setReserved(reserved);
                updateReservation(newReservation.getReservationStatus(), newReservation.getEquipmentQuantity(), newReservation.getDateReserved(), reservationId);
            }
            // HTML Prep
            if (reservations != null) {
                reservations.clear();
            }
            reservations = getReservationsList(reservedId, "reserved");
            reserved.setReservations(reservations);
            model.addAttribute("reserved", reserved);
            model.addAttribute("reservationList", reservations);

            return "reservation/cartView";
        }
    }

    @GetMapping("cartView/{cartViewRequest}")
    public String displayCart(Model model, @PathVariable String cartViewRequest) {
        // Used when clicking the Cart Icon or delete reservation btn
        // Initial Configuartion
        Reserved pendingReserved = null;
        List<Reservation> pendingReservations = null;
        Reservation originalReservation = null;
        var val = cartViewRequest.trim().split("=");
        String cartViewRequestFrom = val[0];
        Integer cartViewRequestID = Integer.valueOf(val[1]);
        model.addAttribute("title", "My Cart");
        //Logic
        if (cartViewRequestFrom.equals("delete")) {
            // Used to delete a reservation and update the reserved object
            originalReservation = getExistingReservationByID(cartViewRequestID, cartViewRequestFrom);
            pendingReserved = getReservedByID(originalReservation.getReservedId(), cartViewRequestFrom);
            pendingReserved.setTotal((reserved.getTotal()) - (originalReservation.getUnitPrice()) * (originalReservation.getEquipmentQuantity()));
            reservedRepository.save(pendingReserved);
            reservationRepository.delete(originalReservation);
            if (reservationRepository.findAllByReservedId(pendingReserved.getId()).toString().isEmpty()) {
                reservedRepository.delete(pendingReserved);
                pendingReserved = null;
                reservedId = null;
            } else {
                pendingReservations = getReservationsList(reservedId, "reserved");
                pendingReserved.setReservations(pendingReservations);
                reservedRepository.save(pendingReserved);
            }


            originalReservation = null;
        }
        // Get reservation and reserved objects
        if (reservedId != null) {
            // Used when a reservation has been made or the reservation list has been updated
            pendingReserved = getReservedByID(reservedId, cartViewRequestFrom);
            if (!reservationRepository.findAllByReservedId(reservedId).toString().isEmpty()) {
                pendingReservations = getReservationsList(reservedId, "reserved");
                pendingReserved.setReservations(pendingReservations);
            }
        } else {
            // Used when a reservation has not yet been made or there is no reserved object
            pendingReserved = new Reserved();
            pendingReserved.setTotal(0);
            reservedRepository.save(pendingReserved);
        }

        // HTML Prep
        reserved = pendingReserved;
        model.addAttribute("reservationList", pendingReservations);
        model.addAttribute("reserved", reserved);
        return "reservation/cartView";
    }

    @GetMapping("resConfirmView")
    public String displayConfirmation(Model model) {
        // Initial Configuration
        model.addAttribute("title", "Reservation Confirmed");
        List<Reservation> confirmedReservations = null;
        Reserved confirmedReserved = null;
        // Logic
        if (!reservationRepository.findAllByReservedId(reservedId).toString().isEmpty()) {
            // Updates all reservation status' as Booked and the reserved status as Active
            confirmedReserved = getReservedByID(reservedId, "Active");
            confirmedReserved.setResStatus("Active");
            reservedRepository.save(confirmedReserved);
            confirmedReservations = getReservationsList(reservedId, "reserved");
            for (Reservation confirmedRes : confirmedReservations) {
                confirmedRes.setReservationStatus("Booked");
                reservationRepository.save(confirmedRes);
            }
        }
        // HTML Prep
        reservedDatesList.clear();
        reservedId = null;
        reservationId = null;
        totalReservedQty = 0;
        model.addAttribute("reservationList", confirmedReservations);
        model.addAttribute("reserved", confirmedReserved);
        return "reservation/resConfirmView";
    }

    public HashMap<String, Integer> getReservedDatesList(String typeOfRequest, Integer recordId) {
        //typeOfRequest = EquipmentId or ReservationId
        //RecordId = Equipment.id Or Reservation.id
        reservedDatesList.clear();
        ArrayList<Object> listOfReservationsByEquip = null;
        Reservation existingReservation = null;

        if (typeOfRequest.equals("EquipmentId")) {
            //typeOfRequest = EquipmentId
            //RecordId = Equipment.id
            //use equipment id to get list of all reservations for that piece of equipment
            //return HashMap<String, Integer> of dates and quantities reserved for equipment ID.
            if (!reservationRepository.findAllByEquipmentId(recordId).toString().isEmpty()) {
                listOfReservationsByEquip = reservationRepository.findAllByEquipmentId(recordId);
                for (var res : listOfReservationsByEquip) {
                    String datesReserved = (String) Array.get(res, 1);
                    Integer qty = (Integer) Array.get(res, 3);
                    reservedDatesList.put(datesReserved, qty);
                    totalReservedQty = totalReservedQty + qty;
                }
            }

        } else if (typeOfRequest.equals("ReservationId")) {
            //typeOfRequest = ReservationId
            //RecordId = Reservation.id
            //Get Reservation Object
            //use Reservation object to get equipment id
            //use equipment id to get list of all reservations for that piece of equipment
            //return HashMap<String, Integer> of dates and quantities reserved for equipment ID.

            existingReservation = getExistingReservationByID(recordId, typeOfRequest);

            if (!reservationRepository.findAllByEquipmentId(existingReservation.getEquipmentId()).toString().isEmpty()) {
                listOfReservationsByEquip = reservationRepository.findAllByEquipmentId(existingReservation.getEquipmentId());
                for (var res : listOfReservationsByEquip) {
                    String datesReserved = (String) Array.get(res, 1);
                    Integer qty = (Integer) Array.get(res, 3);
                    reservedDatesList.put(datesReserved, qty);
                    totalReservedQty = totalReservedQty + qty;
                }
            }
        }

        return reservedDatesList;
    }

    ;

    public Equipment getBuildEquipment(Integer recordId) {
        equipment = null;
        Optional<Equipment> optEquipment = equipmentRepository.findById(recordId);
        if (optEquipment.isPresent()) {
            equipment = optEquipment.get();
        }
        return equipment;
    }

    public Reservation getExistingReservationByID(Integer recordId, String typeOfRequest) {
        // recordID = reservation ID
        // typeOfRequest = Pending-Edit, Pending-Checkout, Delete, ReservationId, EquipmentId
        Reservation orginalReservation = null;
        Optional<Reservation> optReservation = null;
        optReservation = reservationRepository.findById(recordId);
        if (optReservation.isPresent()) {
            orginalReservation = optReservation.get();
            if (typeOfRequest.equals("ReservationId") || typeOfRequest.equals("Pending-Edit")) {
                Integer equipmentIdCheck = orginalReservation.getEquipmentId();
                equipment = getBuildEquipment(orginalReservation.getEquipmentId());
                orginalReservation.setEquipment(equipment);

            }
        }
        return orginalReservation;
    }

    public Reserved getReservedByID(Integer recordId, String typeOfRequest) {
        // recordID = Reserved.id
        // typeOfRequest = Pending-Edit, Pending-Checkout, Delete (a Reservation), ReservedId
        Reserved orginalReserved = null;
        Optional<Reserved> optReserved = null;
        optReserved = reservedRepository.findById(recordId);
        if (optReserved.isPresent()) {
            orginalReserved = optReserved.get();
            orginalReserved.setUser(userRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName()));
            userRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName()).addReserved(orginalReserved);
        }
        return orginalReserved;
    }

    private void updateReservation(String reservationStatus, Integer qty, String dateReserved, Integer reservationId) {
        Reservation updateReservation = null;
        Optional<Reservation> optReservation = null;
        optReservation = reservationRepository.findById(reservationId);
        if (optReservation.isPresent()) {
            updateReservation = optReservation.get();
            updateReservation.setReservationStatus(reservationStatus);
            updateReservation.setEquipmentQuantity(qty);
            updateReservation.setDateReserved(dateReserved);
            reservationRepository.save(updateReservation);
        }
    }

    public List<Reservation> getReservationsList(Integer recordId, String typeOfRequest) {
        List<Reservation> listOfReservations = null;
        if (listOfReservations != null) {
            listOfReservations.clear();
        }

        listOfReservations = reservationRepository.findAllByReservedId(recordId);
        return listOfReservations;
    }
}