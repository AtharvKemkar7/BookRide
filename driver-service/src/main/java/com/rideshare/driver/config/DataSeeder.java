package com.rideshare.driver.config;

import com.rideshare.driver.entity.Driver;
import com.rideshare.driver.entity.DriverStatus;
import com.rideshare.driver.entity.VehicleType;
import com.rideshare.driver.repository.DriverRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataSeeder {

    @Bean
    CommandLineRunner seedDrivers(DriverRepository repository) {
        return args -> {
            if (repository.count() > 0) {
                return;
            }

            Driver marcus = new Driver();
            marcus.setUserId(2L);
            marcus.setFullName("Marcus Hale");
            marcus.setEmail("marcus.hale@rideshare.io");
            marcus.setPhone("+14155550102");
            marcus.setVehicleMakeModel("Toyota Camry 2023");
            marcus.setLicensePlate("7XRT241");
            marcus.setVehicleType(VehicleType.COMFORT);
            marcus.setStatus(DriverStatus.AVAILABLE);
            marcus.setRating(4.97);
            marcus.setCompletedTrips(612);
            marcus.setLatitude(37.7897);
            marcus.setLongitude(-122.3972);
            repository.save(marcus);

            Driver nia = new Driver();
            nia.setUserId(3L);
            nia.setFullName("Nia Okonkwo");
            nia.setEmail("nia.okonkwo@rideshare.io");
            nia.setPhone("+14155550103");
            nia.setVehicleMakeModel("Honda Civic 2022");
            nia.setLicensePlate("8KLM902");
            nia.setVehicleType(VehicleType.ECONOMY);
            nia.setStatus(DriverStatus.AVAILABLE);
            nia.setRating(4.88);
            nia.setCompletedTrips(431);
            nia.setLatitude(37.7749);
            nia.setLongitude(-122.4194);
            repository.save(nia);

            Driver leo = new Driver();
            leo.setUserId(4L);
            leo.setFullName("Leo Park");
            leo.setEmail("leo.park@rideshare.io");
            leo.setPhone("+14155550104");
            leo.setVehicleMakeModel("Tesla Model Y 2024");
            leo.setLicensePlate("EV4U991");
            leo.setVehicleType(VehicleType.PREMIUM);
            leo.setStatus(DriverStatus.EN_ROUTE);
            leo.setRating(4.99);
            leo.setCompletedTrips(890);
            leo.setLatitude(37.7840);
            leo.setLongitude(-122.4090);
            repository.save(leo);
        };
    }
}
