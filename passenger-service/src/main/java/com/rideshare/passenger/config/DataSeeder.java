package com.rideshare.passenger.config;

import com.rideshare.passenger.entity.Passenger;
import com.rideshare.passenger.repository.PassengerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataSeeder {

    @Bean
    CommandLineRunner seedPassengers(PassengerRepository repository) {
        return args -> {
            if (repository.count() > 0) {
                return;
            }
            Passenger ava = new Passenger();
            ava.setUserId(1L);
            ava.setFullName("Ava Chen");
            ava.setEmail("ava.chen@rideshare.io");
            ava.setPhone("+14155550101");
            ava.setHomeAddress("221 Market Street, San Francisco");
            ava.setWorkAddress("1 Market Plaza, San Francisco");
            ava.setPreferredPayment("CARD");
            ava.setRating(4.92);
            ava.setCompletedTrips(48);
            repository.save(ava);
        };
    }
}
