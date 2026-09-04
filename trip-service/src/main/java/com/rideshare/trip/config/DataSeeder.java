package com.rideshare.trip.config;

import com.rideshare.trip.entity.RideType;
import com.rideshare.trip.entity.Trip;
import com.rideshare.trip.entity.TripStatus;
import com.rideshare.trip.repository.TripRepository;
import com.rideshare.trip.service.FareCalculator;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Instant;

@Configuration
public class DataSeeder {

    @Bean
    CommandLineRunner seedTrips(TripRepository repository, FareCalculator fareCalculator) {
        return args -> {
            if (repository.count() > 0) {
                return;
            }

            Trip completed = new Trip();
            completed.setPassengerId(1L);
            completed.setPassengerName("Ava Chen");
            completed.setDriverId(1L);
            completed.setDriverName("Marcus Hale");
            completed.setVehicleLabel("Toyota Camry · 7XRT241");
            completed.setPickupAddress("221 Market Street, San Francisco");
            completed.setDropoffAddress("SFO International Terminal");
            completed.setPickupLat(37.7936);
            completed.setPickupLng(-122.3950);
            completed.setDropoffLat(37.6213);
            completed.setDropoffLng(-122.3790);
            completed.setRideType(RideType.COMFORT);
            completed.setStatus(TripStatus.COMPLETED);
            var airportQuote = fareCalculator.quote(RideType.COMFORT, 37.7936, -122.3950, 37.6213, -122.3790);
            completed.setEstimatedFare(airportQuote.getEstimatedFare());
            completed.setFinalFare(airportQuote.getEstimatedFare());
            completed.setEtaMinutes(airportQuote.getEtaMinutes());
            completed.setDistanceKm(airportQuote.getDistanceKm());
            completed.setStartedAt(Instant.now().minusSeconds(3600));
            completed.setCompletedAt(Instant.now().minusSeconds(2400));
            repository.save(completed);

            Trip live = new Trip();
            live.setPassengerId(1L);
            live.setPassengerName("Ava Chen");
            live.setDriverId(3L);
            live.setDriverName("Leo Park");
            live.setVehicleLabel("Tesla Model Y · EV4U991");
            live.setPickupAddress("Union Square, San Francisco");
            live.setDropoffAddress("Palaces of Fine Arts");
            live.setPickupLat(37.7879);
            live.setPickupLng(-122.4074);
            live.setDropoffLat(37.8029);
            live.setDropoffLng(-122.4484);
            live.setRideType(RideType.PREMIUM);
            live.setStatus(TripStatus.DRIVER_EN_ROUTE);
            var liveQuote = fareCalculator.quote(RideType.PREMIUM, 37.7879, -122.4074, 37.8029, -122.4484);
            live.setEstimatedFare(liveQuote.getEstimatedFare());
            live.setEtaMinutes(liveQuote.getEtaMinutes());
            live.setDistanceKm(liveQuote.getDistanceKm());
            repository.save(live);
        };
    }
}
