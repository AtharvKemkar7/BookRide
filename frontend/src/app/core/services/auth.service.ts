import { Injectable, inject, signal, computed } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { tap } from 'rxjs/operators';
import { AuthResponse, LoginRequest, RegisterRequest, UserProfile, UserRole } from '../models/user.model';

const TOKEN_KEY = 'rideshare.jwt';
const USER_KEY = 'rideshare.user';

interface StoredUser {
  userId: number;
  email: string;
  fullName: string;
  role: UserRole;
}

@Injectable({ providedIn: 'root' })
export class AuthService {
  private readonly http = inject(HttpClient);
  private readonly router = inject(Router);

  readonly currentUser = signal<StoredUser | null>(this.readUser());
  readonly isAuthenticated = computed(() => !!this.currentUser() && !!this.getToken());
  readonly isAdmin = computed(() => this.currentUser()?.role === 'ADMIN');
  readonly isDriver = computed(() => this.currentUser()?.role === 'DRIVER');
  readonly isPassenger = computed(() => this.currentUser()?.role === 'PASSENGER');

  homePath(): string {
    return this.isAdmin() ? '/app/admin' : '/app/dashboard';
  }

  login(payload: LoginRequest) {
    return this.http.post<AuthResponse>('/api/auth/login', payload).pipe(
      tap((response) => this.persistSession(response))
    );
  }

  register(payload: RegisterRequest) {
    return this.http.post<AuthResponse>('/api/auth/register', payload).pipe(
      tap((response) => this.persistSession(response))
    );
  }

  logout(): void {
    localStorage.removeItem(TOKEN_KEY);
    localStorage.removeItem(USER_KEY);
    this.currentUser.set(null);
    void this.router.navigate(['/login']);
  }

  listUsers() {
    return this.http.get<UserProfile[]>('/api/auth/users');
  }

  updateUserStatus(id: number, active: boolean) {
    return this.http.patch<UserProfile>(`/api/auth/users/${id}/status`, { active });
  }

  getToken(): string | null {
    return localStorage.getItem(TOKEN_KEY);
  }

  private persistSession(response: AuthResponse): void {
    localStorage.setItem(TOKEN_KEY, response.token);
    const user: StoredUser = {
      userId: response.userId,
      email: response.email,
      fullName: response.fullName,
      role: response.role
    };
    localStorage.setItem(USER_KEY, JSON.stringify(user));
    this.currentUser.set(user);
  }

  private readUser(): StoredUser | null {
    const raw = localStorage.getItem(USER_KEY);
    if (!raw) {
      return null;
    }
    try {
      return JSON.parse(raw) as StoredUser;
    } catch {
      return null;
    }
  }
}
