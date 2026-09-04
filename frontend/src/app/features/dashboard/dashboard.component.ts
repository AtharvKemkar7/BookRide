import { Component, OnInit, inject, signal } from '@angular/core';
import { RouterLink } from '@angular/router';
import { forkJoin } from 'rxjs';
import { AuthService } from '../../core/services/auth.service';
import { DriverService } from '../../core/services/driver.service';
import { NotificationService } from '../../core/services/notification.service';
import { TripService } from '../../core/services/trip.service';
import { Driver } from '../../core/models/driver.model';
import { Trip } from '../../core/models/trip.model';
import { MapPreviewComponent } from '../../shared/components/map-preview/map-preview.component';

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [RouterLink, MapPreviewComponent],
  templateUrl: './dashboard.component.html',
  styleUrl: './dashboard.component.scss'
})
export class DashboardComponent implements OnInit {
  private readonly tripsApi = inject(TripService);
  private readonly driversApi = inject(DriverService);
  readonly notifications = inject(NotificationService);
  readonly auth = inject(AuthService);

  readonly trips = signal<Trip[]>([]);
  readonly drivers = signal<Driver[]>([]);
  readonly loading = signal(true);

  ngOnInit(): void {
    forkJoin({
      trips: this.tripsApi.list(),
      drivers: this.driversApi.list()
    }).subscribe({
      next: ({ trips, drivers }) => {
        this.trips.set(trips);
        this.drivers.set(drivers);
        this.loading.set(false);
        const live = trips.find((trip) => trip.status !== 'COMPLETED' && trip.status !== 'CANCELLED');
        if (live) {
          this.notifications.pushToast('Live trip update', `${live.status.replaceAll('_', ' ')} · ${live.dropoffAddress}`, 'info');
        }
      },
      error: () => this.loading.set(false)
    });
  }

  get activeTrip(): Trip | undefined {
    return this.trips().find((trip) => trip.status !== 'COMPLETED' && trip.status !== 'CANCELLED');
  }

  get completedCount(): number {
    return this.trips().filter((trip) => trip.status === 'COMPLETED').length;
  }

  get availableDrivers(): number {
    return this.drivers().filter((driver) => driver.status === 'AVAILABLE').length;
  }
}
