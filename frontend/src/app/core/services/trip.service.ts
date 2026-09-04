import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { TripStats } from '../models/user.model';
import { CreateTripRequest, FareQuote, RideType, Trip, TripStatus } from '../models/trip.model';

@Injectable({ providedIn: 'root' })
export class TripService {
  private readonly http = inject(HttpClient);

  list() {
    return this.http.get<Trip[]>('/api/trips');
  }

  stats() {
    return this.http.get<TripStats>('/api/trips/stats');
  }

  get(id: number) {
    return this.http.get<Trip>(`/api/trips/${id}`);
  }

  quote(rideType: RideType, pickupLat: number, pickupLng: number, dropoffLat: number, dropoffLng: number) {
    return this.http.get<FareQuote>('/api/trips/quote', {
      params: { rideType, pickupLat, pickupLng, dropoffLat, dropoffLng }
    });
  }

  requestRide(payload: CreateTripRequest) {
    return this.http.post<Trip>('/api/trips', payload);
  }

  updateStatus(id: number, status: TripStatus, extras: Partial<{ driverId: number; driverName: string; vehicleLabel: string }> = {}) {
    return this.http.patch<Trip>(`/api/trips/${id}/status`, { status, ...extras });
  }
}
