package com.rideshare.trip.repository;

import com.rideshare.trip.entity.Trip;
import com.rideshare.trip.entity.TripStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TripRepository extends JpaRepository<Trip, Long> {

    List<Trip> findAllByOrderByCreatedAtDesc();

    List<Trip> findByPassengerIdOrderByCreatedAtDesc(Long passengerId);

    List<Trip> findByDriverIdOrderByCreatedAtDesc(Long driverId);

    List<Trip> findByStatus(TripStatus status);
}
