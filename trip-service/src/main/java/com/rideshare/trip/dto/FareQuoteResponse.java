package com.rideshare.trip.dto;

import com.rideshare.trip.entity.RideType;

import java.math.BigDecimal;

public class FareQuoteResponse {

    private RideType rideType;
    private BigDecimal estimatedFare;
    private int etaMinutes;
    private double distanceKm;
    private String currency = "USD";

    public FareQuoteResponse() {
    }

    public FareQuoteResponse(RideType rideType, BigDecimal estimatedFare, int etaMinutes, double distanceKm) {
        this.rideType = rideType;
        this.estimatedFare = estimatedFare;
        this.etaMinutes = etaMinutes;
        this.distanceKm = distanceKm;
    }

    public RideType getRideType() {
        return rideType;
    }

    public void setRideType(RideType rideType) {
        this.rideType = rideType;
    }

    public BigDecimal getEstimatedFare() {
        return estimatedFare;
    }

    public void setEstimatedFare(BigDecimal estimatedFare) {
        this.estimatedFare = estimatedFare;
    }

    public int getEtaMinutes() {
        return etaMinutes;
    }

    public void setEtaMinutes(int etaMinutes) {
        this.etaMinutes = etaMinutes;
    }

    public double getDistanceKm() {
        return distanceKm;
    }

    public void setDistanceKm(double distanceKm) {
        this.distanceKm = distanceKm;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}
