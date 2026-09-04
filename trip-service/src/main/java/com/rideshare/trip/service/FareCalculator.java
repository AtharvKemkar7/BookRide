package com.rideshare.trip.service;

import com.rideshare.trip.dto.FareQuoteResponse;
import com.rideshare.trip.entity.RideType;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Component
public class FareCalculator {

    public FareQuoteResponse quote(RideType rideType, double pickupLat, double pickupLng, double dropoffLat, double dropoffLng) {
        double distanceKm = haversineKm(pickupLat, pickupLng, dropoffLat, dropoffLng);
        double multiplier = switch (rideType) {
            case ECONOMY -> 1.0;
            case COMFORT -> 1.35;
            case XL -> 1.7;
            case PREMIUM -> 2.2;
        };
        double raw = (2.75 + (distanceKm * 1.85) + 0.45) * multiplier;
        BigDecimal fare = BigDecimal.valueOf(raw).setScale(2, RoundingMode.HALF_UP);
        int eta = Math.max(4, (int) Math.round(distanceKm * 2.4) + 3);
        return new FareQuoteResponse(rideType, fare, eta, round(distanceKm));
    }

    private double haversineKm(double lat1, double lon1, double lat2, double lon2) {
        double earthRadius = 6371.0;
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(dLon / 2) * Math.sin(dLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return earthRadius * c;
    }

    private double round(double value) {
        return BigDecimal.valueOf(value).setScale(2, RoundingMode.HALF_UP).doubleValue();
    }
}
