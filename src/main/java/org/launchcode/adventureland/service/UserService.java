package org.launchcode.adventureland.service;

import org.launchcode.adventureland.dto.UserRegistrationDto;
import org.launchcode.adventureland.models.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {

    User save(UserRegistrationDto registrationDto);
    List<User> getAll();

}
