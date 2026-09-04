package com.rideshare.trip.controller;

import com.rideshare.trip.dto.CreateTripRequest;
import com.rideshare.trip.dto.FareQuoteResponse;
import com.rideshare.trip.dto.TripStatsResponse;
import com.rideshare.trip.dto.UpdateTripStatusRequest;
import com.rideshare.trip.entity.RideType;
import com.rideshare.trip.entity.Trip;
import com.rideshare.trip.service.TripService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/trips")
public class TripController {

    private final TripService tripService;

    public TripController(TripService tripService) {
        this.tripService = tripService;
    }

    @GetMapping
    public List<Trip> list() {
        return tripService.findAll();
    }

    @GetMapping("/stats")
    public TripStatsResponse stats() {
        return tripService.stats();
    }

    @GetMapping("/{id}")
    public Trip get(@PathVariable Long id) {
        return tripService.findById(id);
    }

    @GetMapping("/quote")
    public FareQuoteResponse quote(
            @RequestParam RideType rideType,
            @RequestParam double pickupLat,
            @RequestParam double pickupLng,
            @RequestParam double dropoffLat,
            @RequestParam double dropoffLng) {
        return tripService.quote(rideType, pickupLat, pickupLng, dropoffLat, dropoffLng);
    }

    @PostMapping
    public ResponseEntity<Trip> requestRide(@Valid @RequestBody CreateTripRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(tripService.requestRide(request));
    }

    @PatchMapping("/{id}/status")
    public Trip updateStatus(@PathVariable Long id, @Valid @RequestBody UpdateTripStatusRequest request) {
        return tripService.updateStatus(id, request);
    }
}
