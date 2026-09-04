package com.rideshare.driver.service;

import com.rideshare.driver.dto.DriverRequest;
import com.rideshare.driver.dto.LocationUpdateRequest;
import com.rideshare.driver.entity.Driver;
import com.rideshare.driver.entity.DriverStatus;
import com.rideshare.driver.entity.VehicleType;
import com.rideshare.driver.exception.ApiException;
import com.rideshare.driver.repository.DriverRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

@Service
public class DriverService {

    private final DriverRepository driverRepository;

    public DriverService(DriverRepository driverRepository) {
        this.driverRepository = driverRepository;
    }

    @Transactional(readOnly = true)
    public List<Driver> findAll() {
        return driverRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<Driver> findAvailable() {
        return driverRepository.findByStatus(DriverStatus.AVAILABLE);
    }

    @Transactional(readOnly = true)
    public Driver findById(Long id) {
        return driverRepository.findById(id)
                .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "Driver not found"));
    }

    @Transactional
    public Driver create(DriverRequest request) {
        Driver driver = new Driver();
        apply(driver, request);
        driver.setStatus(DriverStatus.AVAILABLE);
        return driverRepository.save(driver);
    }

    @Transactional
    public Driver updateStatus(Long id, DriverStatus status) {
        Driver driver = findById(id);
        driver.setStatus(status);
        driver.setLastSeenAt(Instant.now());
        return driverRepository.save(driver);
    }

    @Transactional
    public Driver updateLocation(Long id, LocationUpdateRequest request) {
        Driver driver = findById(id);
        driver.setLatitude(request.getLatitude());
        driver.setLongitude(request.getLongitude());
        driver.setLastSeenAt(Instant.now());
        return driverRepository.save(driver);
    }

    private void apply(Driver driver, DriverRequest request) {
        driver.setUserId(request.getUserId());
        driver.setFullName(request.getFullName());
        driver.setEmail(request.getEmail());
        driver.setPhone(request.getPhone());
        driver.setVehicleMakeModel(request.getVehicleMakeModel());
        driver.setLicensePlate(request.getLicensePlate());
        driver.setVehicleType(request.getVehicleType() == null ? VehicleType.ECONOMY : request.getVehicleType());
        if (request.getLatitude() != null) {
            driver.setLatitude(request.getLatitude());
        }
        if (request.getLongitude() != null) {
            driver.setLongitude(request.getLongitude());
        }
    }
}
