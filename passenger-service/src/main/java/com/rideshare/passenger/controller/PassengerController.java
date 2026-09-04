package com.rideshare.passenger.controller;

import com.rideshare.passenger.dto.PassengerRequest;
import com.rideshare.passenger.entity.Passenger;
import com.rideshare.passenger.service.PassengerService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/passengers")
public class PassengerController {

    private final PassengerService passengerService;

    public PassengerController(PassengerService passengerService) {
        this.passengerService = passengerService;
    }

    @GetMapping
    public List<Passenger> list() {
        return passengerService.findAll();
    }

    @GetMapping("/{id}")
    public Passenger get(@PathVariable Long id) {
        return passengerService.findById(id);
    }

    @PostMapping
    public ResponseEntity<Passenger> create(@Valid @RequestBody PassengerRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(passengerService.create(request));
    }

    @PutMapping("/{id}")
    public Passenger update(@PathVariable Long id, @Valid @RequestBody PassengerRequest request) {
        return passengerService.update(id, request);
    }
}
