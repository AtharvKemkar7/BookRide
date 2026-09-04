import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { NavbarComponent } from '../../shared/components/navbar/navbar.component';
import { ToastComponent } from '../../shared/components/toast/toast.component';

@Component({
  selector: 'app-shell',
  standalone: true,
  imports: [RouterOutlet, NavbarComponent, ToastComponent],
  template: `
    <app-navbar />
    <app-toast />
    <main class="page">
      <router-outlet />
    </main>
  `,
  styles: [`
    .page {
      padding: 1.5rem 4vw 3rem;
    }
  `]
})
export class ShellComponent {}
