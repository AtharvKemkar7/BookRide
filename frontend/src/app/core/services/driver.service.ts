import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Driver } from '../models/driver.model';

@Injectable({ providedIn: 'root' })
export class DriverService {
  private readonly http = inject(HttpClient);

  list() {
    return this.http.get<Driver[]>('/api/drivers');
  }

  available() {
    return this.http.get<Driver[]>('/api/drivers/available');
  }
}
