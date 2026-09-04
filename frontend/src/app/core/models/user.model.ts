export type UserRole = 'PASSENGER' | 'DRIVER' | 'ADMIN';

export interface AuthResponse {
  token: string;
  tokenType: string;
  userId: number;
  email: string;
  fullName: string;
  role: UserRole;
  expiresInMs: number;
}

export interface LoginRequest {
  email: string;
  password: string;
}

export interface RegisterRequest {
  fullName: string;
  email: string;
  phone: string;
  password: string;
  role: UserRole;
}

export interface UserProfile {
  id: number;
  email: string;
  phone: string;
  fullName: string;
  role: UserRole;
  active: boolean;
}

export interface TripStats {
  totalTrips: number;
  requested: number;
  inProgress: number;
  completed: number;
  cancelled: number;
  completedRevenue: number;
}
