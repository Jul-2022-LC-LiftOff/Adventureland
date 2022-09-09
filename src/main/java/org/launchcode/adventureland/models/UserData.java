package org.launchcode.adventureland.models;

import org.launchcode.adventureland.models.data.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class UserData {

    @Autowired
    private UserRepository userRepository;
    public static boolean isUserNotLoggedIn() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (authentication == null || authentication instanceof AnonymousAuthenticationToken);
    }

}
