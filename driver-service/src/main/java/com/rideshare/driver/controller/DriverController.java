package com.rideshare.driver.controller;

import com.rideshare.driver.dto.DriverRequest;
import com.rideshare.driver.dto.LocationUpdateRequest;
import com.rideshare.driver.entity.Driver;
import com.rideshare.driver.entity.DriverStatus;
import com.rideshare.driver.service.DriverService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/drivers")
public class DriverController {

    private final DriverService driverService;

    public DriverController(DriverService driverService) {
        this.driverService = driverService;
    }

    @GetMapping
    public List<Driver> list() {
        return driverService.findAll();
    }

    @GetMapping("/available")
    public List<Driver> available() {
        return driverService.findAvailable();
    }

    @GetMapping("/{id}")
    public Driver get(@PathVariable Long id) {
        return driverService.findById(id);
    }

    @PostMapping
    public ResponseEntity<Driver> create(@Valid @RequestBody DriverRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(driverService.create(request));
    }

    @PatchMapping("/{id}/status")
    public Driver updateStatus(@PathVariable Long id, @RequestParam DriverStatus status) {
        return driverService.updateStatus(id, status);
    }

    @PatchMapping("/{id}/location")
    public Driver updateLocation(@PathVariable Long id, @Valid @RequestBody LocationUpdateRequest request) {
        return driverService.updateLocation(id, request);
    }
}
