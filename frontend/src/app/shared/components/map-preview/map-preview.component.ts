import { Component, Input } from '@angular/core';

@Component({
  selector: 'app-map-preview',
  standalone: true,
  templateUrl: './map-preview.component.html',
  styleUrl: './map-preview.component.scss'
})
export class MapPreviewComponent {
  @Input() pickup = 'Pickup';
  @Input() dropoff = 'Dropoff';
  @Input() eta = 8;
}
