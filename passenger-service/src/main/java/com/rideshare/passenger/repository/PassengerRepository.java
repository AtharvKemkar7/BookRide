package com.rideshare.passenger.repository;

import com.rideshare.passenger.entity.Passenger;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PassengerRepository extends JpaRepository<Passenger, Long> {

    Optional<Passenger> findByUserId(Long userId);

    Optional<Passenger> findByEmailIgnoreCase(String email);
}
