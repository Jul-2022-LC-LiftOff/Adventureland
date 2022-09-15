package org.launchcode.adventureland.service;


import org.launchcode.adventureland.dto.UserRegistrationDto;
import org.launchcode.adventureland.models.Role;
import org.launchcode.adventureland.models.User;
import org.launchcode.adventureland.models.data.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static org.launchcode.adventureland.models.Role.USER;

@Service
public class UserServiceImpl implements UserService{

    private UserRepository userRepository;


    @Lazy
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;


    public UserServiceImpl(@Lazy UserRepository userRepository) {
        super();
        this.userRepository = userRepository;
    }

    @Override
    public User save(UserRegistrationDto registrationDto) {

        User user = new User(registrationDto.getFirstName(), registrationDto.getLastName(), registrationDto.getEmail(), passwordEncoder.encode(registrationDto.getPassword()), registrationDto.getBirthdate(), USER);
        return userRepository.save(user);
    }

    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username);
        if (user == null) {
            throw new UsernameNotFoundException("No user found with email " + username);
        }
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), getAuthorities(username));
    }

    private Collection<? extends GrantedAuthority> getAuthorities(String email) {
        User user = userRepository.findByEmail(email);
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(user.getRole().toString()));
        return authorities;
    }
}
