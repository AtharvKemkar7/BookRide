import { Component, OnInit, inject, signal } from '@angular/core';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { AuthService } from '../../core/services/auth.service';
import { NotificationService } from '../../core/services/notification.service';
import { TripService } from '../../core/services/trip.service';
import { FareQuote, RideType, Trip } from '../../core/models/trip.model';
import { MapPreviewComponent } from '../../shared/components/map-preview/map-preview.component';
import { CurrencyPipe } from '@angular/common';

interface Place {
  label: string;
  lat: number;
  lng: number;
}

@Component({
  selector: 'app-book-ride',
  standalone: true,
  imports: [ReactiveFormsModule, MapPreviewComponent, CurrencyPipe],
  templateUrl: './book-ride.component.html',
  styleUrl: './book-ride.component.scss'
})
export class BookRideComponent implements OnInit {
  private readonly fb = inject(FormBuilder);
  private readonly tripsApi = inject(TripService);
  private readonly notifications = inject(NotificationService);
  readonly auth = inject(AuthService);

  readonly places: Place[] = [
    { label: '221 Market Street', lat: 37.7936, lng: -122.3950 },
    { label: 'Union Square', lat: 37.7879, lng: -122.4074 },
    { label: 'SFO International Terminal', lat: 37.6213, lng: -122.3790 },
    { label: 'Palace of Fine Arts', lat: 37.8029, lng: -122.4484 },
    { label: 'Mission Dolores Park', lat: 37.7596, lng: -122.4269 }
  ];

  readonly rideTypes: RideType[] = ['ECONOMY', 'COMFORT', 'XL', 'PREMIUM'];
  readonly quote = signal<FareQuote | null>(null);
  readonly trip = signal<Trip | null>(null);
  readonly loading = signal(false);

  readonly form = this.fb.nonNullable.group({
    pickup: [this.places[0].label, Validators.required],
    dropoff: [this.places[2].label, Validators.required],
    rideType: ['COMFORT' as RideType, Validators.required]
  });

  ngOnInit(): void {
    this.refreshQuote();
  }

  refreshQuote(): void {
    const pickup = this.findPlace(this.form.controls.pickup.value);
    const dropoff = this.findPlace(this.form.controls.dropoff.value);
    if (!pickup || !dropoff) {
      return;
    }
    this.tripsApi.quote(this.form.controls.rideType.value, pickup.lat, pickup.lng, dropoff.lat, dropoff.lng)
      .subscribe((quote) => this.quote.set(quote));
  }

  book(): void {
    const pickup = this.findPlace(this.form.controls.pickup.value);
    const dropoff = this.findPlace(this.form.controls.dropoff.value);
    const user = this.auth.currentUser();
    if (!pickup || !dropoff || !user) {
      return;
    }
    this.loading.set(true);
    this.tripsApi.requestRide({
      passengerId: user.userId,
      passengerName: user.fullName,
      pickupAddress: pickup.label,
      dropoffAddress: dropoff.label,
      pickupLat: pickup.lat,
      pickupLng: pickup.lng,
      dropoffLat: dropoff.lat,
      dropoffLng: dropoff.lng,
      rideType: this.form.controls.rideType.value
    }).subscribe({
      next: (trip) => {
        this.trip.set(trip);
        this.loading.set(false);
        this.notifications.pushToast('Ride Requested', `Looking for drivers to ${trip.dropoffAddress}`, 'success');
      },
      error: () => this.loading.set(false)
    });
  }

  private findPlace(label: string): Place | undefined {
    return this.places.find((place) => place.label === label);
  }
}
