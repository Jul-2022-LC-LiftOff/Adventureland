package org.launchcode.adventureland.models;

import org.launchcode.adventureland.models.data.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.ArrayList;
import java.util.List;

public class UserData {

    @Autowired
    private UserRepository userRepository;
    public static boolean isUserNotLoggedIn() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (authentication == null || authentication instanceof AnonymousAuthenticationToken);
    }

    public static List<Reservation> getReservationList(User user) {
        List<Reserved> reservedList = user.getReserved();
        List<Reservation> reservationsList = new ArrayList<>();
       for (int i = 0; i < reservedList.size(); i++) {
           reservationsList.addAll(reservedList.get(i).getReservations());
       }
       return reservationsList;
    }

}
