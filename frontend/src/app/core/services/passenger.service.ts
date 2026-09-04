import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Passenger } from '../models/driver.model';

@Injectable({ providedIn: 'root' })
export class PassengerService {
  private readonly http = inject(HttpClient);

  list() {
    return this.http.get<Passenger[]>('/api/passengers');
  }
}
