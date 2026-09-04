export type DriverStatus = 'OFFLINE' | 'AVAILABLE' | 'EN_ROUTE' | 'ON_TRIP';
export type VehicleType = 'ECONOMY' | 'COMFORT' | 'XL' | 'PREMIUM';

export interface Driver {
  id: number;
  userId: number;
  fullName: string;
  email: string;
  phone: string;
  vehicleMakeModel: string;
  licensePlate: string;
  vehicleType: VehicleType;
  status: DriverStatus;
  rating: number;
  completedTrips: number;
  latitude: number;
  longitude: number;
  lastSeenAt: string;
}

export interface Passenger {
  id: number;
  userId: number;
  fullName: string;
  email: string;
  phone: string;
  homeAddress?: string;
  workAddress?: string;
  preferredPayment: string;
  rating: number;
  completedTrips: number;
}

export interface AppNotification {
  id: number;
  eventType: string;
  recipient: string;
  channel: string;
  title: string;
  message: string;
  tripId?: number;
  status: string;
  createdAt: string;
}

export interface ToastMessage {
  id: number;
  title: string;
  body: string;
  tone: 'info' | 'success' | 'warn';
}
