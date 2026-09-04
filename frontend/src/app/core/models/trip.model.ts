export type TripStatus =
  | 'REQUESTED'
  | 'MATCHED'
  | 'DRIVER_EN_ROUTE'
  | 'ARRIVED'
  | 'IN_PROGRESS'
  | 'COMPLETED'
  | 'CANCELLED';

export type RideType = 'ECONOMY' | 'COMFORT' | 'XL' | 'PREMIUM';

export interface Trip {
  id: number;
  passengerId: number;
  driverId?: number;
  pickupAddress: string;
  dropoffAddress: string;
  pickupLat: number;
  pickupLng: number;
  dropoffLat: number;
  dropoffLng: number;
  status: TripStatus;
  rideType: RideType;
  estimatedFare: number;
  finalFare?: number;
  etaMinutes: number;
  distanceKm: number;
  passengerName?: string;
  driverName?: string;
  vehicleLabel?: string;
  createdAt: string;
  updatedAt: string;
  startedAt?: string;
  completedAt?: string;
}

export interface CreateTripRequest {
  passengerId: number;
  passengerName: string;
  pickupAddress: string;
  dropoffAddress: string;
  pickupLat: number;
  pickupLng: number;
  dropoffLat: number;
  dropoffLng: number;
  rideType: RideType;
}

export interface FareQuote {
  rideType: RideType;
  estimatedFare: number;
  etaMinutes: number;
  distanceKm: number;
  currency: string;
}
