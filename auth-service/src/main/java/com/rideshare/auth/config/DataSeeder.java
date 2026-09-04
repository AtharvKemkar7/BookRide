package com.rideshare.auth.config;

import com.rideshare.auth.entity.Role;
import com.rideshare.auth.entity.UserAccount;
import com.rideshare.auth.repository.UserAccountRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataSeeder {

    @Bean
    CommandLineRunner seedUsers(UserAccountRepository repository, PasswordEncoder encoder) {
        return args -> {
            if (repository.count() > 0) {
                return;
            }
            UserAccount passenger = new UserAccount();
            passenger.setFullName("Ava Chen");
            passenger.setEmail("ava.chen@rideshare.io");
            passenger.setPhone("+14155550101");
            passenger.setPasswordHash(encoder.encode("Passenger1!"));
            passenger.setRole(Role.PASSENGER);
            repository.save(passenger);

            UserAccount driver = new UserAccount();
            driver.setFullName("Marcus Hale");
            driver.setEmail("marcus.hale@rideshare.io");
            driver.setPhone("+14155550102");
            driver.setPasswordHash(encoder.encode("Driver1!"));
            driver.setRole(Role.DRIVER);
            repository.save(driver);

            UserAccount admin = new UserAccount();
            admin.setFullName("Jordan Reid");
            admin.setEmail("jordan.reid@rideshare.io");
            admin.setPhone("+14155550999");
            admin.setPasswordHash(encoder.encode("Admin1!"));
            admin.setRole(Role.ADMIN);
            repository.save(admin);
        };
    }
}
