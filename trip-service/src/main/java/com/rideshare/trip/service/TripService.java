package com.rideshare.trip.service;

import com.rideshare.trip.dto.CreateTripRequest;
import com.rideshare.trip.dto.FareQuoteResponse;
import com.rideshare.trip.dto.TripStatsResponse;
import com.rideshare.trip.dto.UpdateTripStatusRequest;
import com.rideshare.trip.entity.RideType;
import com.rideshare.trip.entity.Trip;
import com.rideshare.trip.entity.TripStatus;
import com.rideshare.trip.exception.ApiException;
import com.rideshare.trip.repository.TripRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

@Service
public class TripService {

    private final TripRepository tripRepository;
    private final FareCalculator fareCalculator;
    private final TripNotificationPublisher notificationPublisher;

    public TripService(
            TripRepository tripRepository,
            FareCalculator fareCalculator,
            TripNotificationPublisher notificationPublisher) {
        this.tripRepository = tripRepository;
        this.fareCalculator = fareCalculator;
        this.notificationPublisher = notificationPublisher;
    }

    @Transactional(readOnly = true)
    public List<Trip> findAll() {
        return tripRepository.findAllByOrderByCreatedAtDesc();
    }

    @Transactional(readOnly = true)
    public TripStatsResponse stats() {
        List<Trip> trips = tripRepository.findAll();
        TripStatsResponse stats = new TripStatsResponse();
        stats.setTotalTrips(trips.size());
        stats.setRequested(trips.stream().filter(trip -> trip.getStatus() == TripStatus.REQUESTED).count());
        stats.setInProgress(trips.stream().filter(trip ->
                trip.getStatus() == TripStatus.IN_PROGRESS
                        || trip.getStatus() == TripStatus.DRIVER_EN_ROUTE
                        || trip.getStatus() == TripStatus.ARRIVED
                        || trip.getStatus() == TripStatus.MATCHED).count());
        stats.setCompleted(trips.stream().filter(trip -> trip.getStatus() == TripStatus.COMPLETED).count());
        stats.setCancelled(trips.stream().filter(trip -> trip.getStatus() == TripStatus.CANCELLED).count());
        BigDecimal revenue = trips.stream()
                .filter(trip -> trip.getStatus() == TripStatus.COMPLETED)
                .map(trip -> trip.getFinalFare() != null ? trip.getFinalFare() : trip.getEstimatedFare())
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        stats.setCompletedRevenue(revenue);
        return stats;
    }

    @Transactional(readOnly = true)
    public Trip findById(Long id) {
        return tripRepository.findById(id)
                .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "Trip not found"));
    }

    public FareQuoteResponse quote(RideType rideType, double pickupLat, double pickupLng, double dropoffLat, double dropoffLng) {
        return fareCalculator.quote(rideType == null ? RideType.ECONOMY : rideType, pickupLat, pickupLng, dropoffLat, dropoffLng);
    }

    @Transactional
    public Trip requestRide(CreateTripRequest request) {
        RideType rideType = request.getRideType() == null ? RideType.ECONOMY : request.getRideType();
        FareQuoteResponse quote = fareCalculator.quote(
                rideType,
                request.getPickupLat(),
                request.getPickupLng(),
                request.getDropoffLat(),
                request.getDropoffLng()
        );
        Trip trip = new Trip();
        trip.setPassengerId(request.getPassengerId());
        trip.setPassengerName(request.getPassengerName());
        trip.setPickupAddress(request.getPickupAddress());
        trip.setDropoffAddress(request.getDropoffAddress());
        trip.setPickupLat(request.getPickupLat());
        trip.setPickupLng(request.getPickupLng());
        trip.setDropoffLat(request.getDropoffLat());
        trip.setDropoffLng(request.getDropoffLng());
        trip.setRideType(rideType);
        trip.setStatus(TripStatus.REQUESTED);
        trip.setEstimatedFare(quote.getEstimatedFare());
        trip.setEtaMinutes(quote.getEtaMinutes());
        trip.setDistanceKm(quote.getDistanceKm());
        Trip saved = tripRepository.save(trip);
        notificationPublisher.notifyStatusChange(saved);
        return saved;
    }

    @Transactional
    public Trip updateStatus(Long id, UpdateTripStatusRequest request) {
        Trip trip = findById(id);
        TripStatus next = request.getStatus();
        trip.setStatus(next);
        if (request.getDriverId() != null) {
            trip.setDriverId(request.getDriverId());
        }
        if (request.getDriverName() != null) {
            trip.setDriverName(request.getDriverName());
        }
        if (request.getVehicleLabel() != null) {
            trip.setVehicleLabel(request.getVehicleLabel());
        }
        if (next == TripStatus.IN_PROGRESS) {
            trip.setStartedAt(Instant.now());
        }
        if (next == TripStatus.COMPLETED) {
            trip.setCompletedAt(Instant.now());
            trip.setFinalFare(trip.getEstimatedFare());
        }
        Trip saved = tripRepository.save(trip);
        notificationPublisher.notifyStatusChange(saved);
        return saved;
    }
}
