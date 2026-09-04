import { Component, OnInit, inject, signal } from '@angular/core';
import { DriverService } from '../../core/services/driver.service';
import { Driver } from '../../core/models/driver.model';

@Component({
  selector: 'app-drivers',
  standalone: true,
  templateUrl: './drivers.component.html',
  styleUrl: './drivers.component.scss'
})
export class DriversComponent implements OnInit {
  private readonly driversApi = inject(DriverService);
  readonly drivers = signal<Driver[]>([]);

  ngOnInit(): void {
    this.driversApi.list().subscribe((drivers) => this.drivers.set(drivers));
  }
}
