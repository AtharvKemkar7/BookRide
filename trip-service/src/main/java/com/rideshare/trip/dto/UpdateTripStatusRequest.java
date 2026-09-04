package com.rideshare.trip.dto;

import com.rideshare.trip.entity.TripStatus;
import jakarta.validation.constraints.NotNull;

public class UpdateTripStatusRequest {

    @NotNull
    private TripStatus status;

    private Long driverId;
    private String driverName;
    private String vehicleLabel;

    public TripStatus getStatus() {
        return status;
    }

    public void setStatus(TripStatus status) {
        this.status = status;
    }

    public Long getDriverId() {
        return driverId;
    }

    public void setDriverId(Long driverId) {
        this.driverId = driverId;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public String getVehicleLabel() {
        return vehicleLabel;
    }

    public void setVehicleLabel(String vehicleLabel) {
        this.vehicleLabel = vehicleLabel;
    }
}
