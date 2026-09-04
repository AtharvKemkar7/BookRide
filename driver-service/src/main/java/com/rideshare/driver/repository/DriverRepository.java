package com.rideshare.driver.repository;

import com.rideshare.driver.entity.Driver;
import com.rideshare.driver.entity.DriverStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DriverRepository extends JpaRepository<Driver, Long> {

    List<Driver> findByStatus(DriverStatus status);

    Optional<Driver> findByUserId(Long userId);
}
