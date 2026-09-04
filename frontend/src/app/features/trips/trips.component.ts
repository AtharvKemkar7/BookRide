import { Component, OnInit, inject, signal } from '@angular/core';
import { CurrencyPipe, DatePipe } from '@angular/common';
import { NotificationService } from '../../core/services/notification.service';
import { TripService } from '../../core/services/trip.service';
import { Trip, TripStatus } from '../../core/models/trip.model';

@Component({
  selector: 'app-trips',
  standalone: true,
  imports: [CurrencyPipe, DatePipe],
  templateUrl: './trips.component.html',
  styleUrl: './trips.component.scss'
})
export class TripsComponent implements OnInit {
  private readonly tripsApi = inject(TripService);
  private readonly notifications = inject(NotificationService);
  readonly trips = signal<Trip[]>([]);

  ngOnInit(): void {
    this.reload();
  }

  advance(trip: Trip): void {
    const sequence: TripStatus[] = ['REQUESTED', 'MATCHED', 'DRIVER_EN_ROUTE', 'ARRIVED', 'IN_PROGRESS', 'COMPLETED'];
    const index = sequence.indexOf(trip.status);
    const next = sequence[Math.min(index + 1, sequence.length - 1)];
    this.tripsApi.updateStatus(trip.id, next, {
      driverId: trip.driverId ?? 1,
      driverName: trip.driverName ?? 'Marcus Hale',
      vehicleLabel: trip.vehicleLabel ?? 'Toyota Camry · 7XRT241'
    }).subscribe((updated) => {
      this.trips.update((items) => items.map((item) => item.id === updated.id ? updated : item));
      if (updated.status === 'COMPLETED' || updated.status === 'IN_PROGRESS') {
        this.notifications.pushToast(
          updated.status === 'COMPLETED' ? 'Trip Completed' : 'Trip Started',
          updated.dropoffAddress,
          'success'
        );
      }
    });
  }

  private reload(): void {
    this.tripsApi.list().subscribe((trips) => this.trips.set(trips));
  }
}
