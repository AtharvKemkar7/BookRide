import { Component, OnInit, inject, signal } from '@angular/core';
import { CurrencyPipe, DatePipe } from '@angular/common';
import { forkJoin } from 'rxjs';
import { AuthService } from '../../core/services/auth.service';
import { DriverService } from '../../core/services/driver.service';
import { NotificationService } from '../../core/services/notification.service';
import { PassengerService } from '../../core/services/passenger.service';
import { TripService } from '../../core/services/trip.service';
import { AppNotification, Driver, Passenger } from '../../core/models/driver.model';
import { Trip } from '../../core/models/trip.model';
import { TripStats, UserProfile } from '../../core/models/user.model';

@Component({
  selector: 'app-admin',
  standalone: true,
  imports: [CurrencyPipe, DatePipe],
  templateUrl: './admin.component.html',
  styleUrl: './admin.component.scss'
})
export class AdminComponent implements OnInit {
  private readonly authApi = inject(AuthService);
  private readonly tripsApi = inject(TripService);
  private readonly driversApi = inject(DriverService);
  private readonly passengersApi = inject(PassengerService);
  private readonly notificationsApi = inject(NotificationService);

  readonly users = signal<UserProfile[]>([]);
  readonly drivers = signal<Driver[]>([]);
  readonly passengers = signal<Passenger[]>([]);
  readonly trips = signal<Trip[]>([]);
  readonly alerts = signal<AppNotification[]>([]);
  readonly stats = signal<TripStats | null>(null);
  readonly loading = signal(true);

  ngOnInit(): void {
    forkJoin({
      users: this.authApi.listUsers(),
      drivers: this.driversApi.list(),
      passengers: this.passengersApi.list(),
      trips: this.tripsApi.list(),
      alerts: this.notificationsApi.list(),
      stats: this.tripsApi.stats()
    }).subscribe({
      next: (data) => {
        this.users.set(data.users);
        this.drivers.set(data.drivers);
        this.passengers.set(data.passengers);
        this.trips.set(data.trips);
        this.alerts.set(data.alerts);
        this.stats.set(data.stats);
        this.loading.set(false);
      },
      error: () => this.loading.set(false)
    });
  }

  toggleUser(user: UserProfile): void {
    this.authApi.updateUserStatus(user.id, !user.active).subscribe((updated) => {
      this.users.update((items) => items.map((item) => item.id === updated.id ? updated : item));
      this.notificationsApi.pushToast(
        updated.active ? 'Account restored' : 'Account suspended',
        updated.fullName,
        updated.active ? 'success' : 'warn'
      );
    });
  }
}
