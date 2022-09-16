package org.launchcode.adventureland;

import org.launchcode.adventureland.config.SecurityConfiguration;
import org.launchcode.adventureland.models.Role;
import org.launchcode.adventureland.models.User;
import org.launchcode.adventureland.models.data.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class UserTableLoader {

    private UserRepository userRepository;

    public BCryptPasswordEncoder thePasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    public UserTableLoader(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Bean
    public CommandLineRunner initializeDatabase() {
        return args -> {
            User admin = new User("admin", "admin", "admin@admin.com", thePasswordEncoder().encode("password123"), "2000-01-01", Role.ADMIN);
            userRepository.save(admin);
        };
    }
}
