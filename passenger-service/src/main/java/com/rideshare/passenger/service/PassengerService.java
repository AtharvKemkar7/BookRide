package com.rideshare.passenger.service;

import com.rideshare.passenger.dto.PassengerRequest;
import com.rideshare.passenger.entity.Passenger;
import com.rideshare.passenger.exception.ApiException;
import com.rideshare.passenger.repository.PassengerRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PassengerService {

    private final PassengerRepository passengerRepository;

    public PassengerService(PassengerRepository passengerRepository) {
        this.passengerRepository = passengerRepository;
    }

    @Transactional(readOnly = true)
    public List<Passenger> findAll() {
        return passengerRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Passenger findById(Long id) {
        return passengerRepository.findById(id)
                .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "Passenger not found"));
    }

    @Transactional
    public Passenger create(PassengerRequest request) {
        Passenger passenger = new Passenger();
        apply(passenger, request);
        return passengerRepository.save(passenger);
    }

    @Transactional
    public Passenger update(Long id, PassengerRequest request) {
        Passenger passenger = findById(id);
        apply(passenger, request);
        return passengerRepository.save(passenger);
    }

    private void apply(Passenger passenger, PassengerRequest request) {
        passenger.setUserId(request.getUserId());
        passenger.setFullName(request.getFullName());
        passenger.setEmail(request.getEmail());
        passenger.setPhone(request.getPhone());
        passenger.setHomeAddress(request.getHomeAddress());
        passenger.setWorkAddress(request.getWorkAddress());
        if (request.getPreferredPayment() != null) {
            passenger.setPreferredPayment(request.getPreferredPayment());
        }
    }
}
