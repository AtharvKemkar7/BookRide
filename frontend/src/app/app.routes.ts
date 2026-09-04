import { Routes } from '@angular/router';
import { adminGuard, authGuard, guestGuard } from './core/guards/auth.guard';

export const routes: Routes = [
  { path: '', pathMatch: 'full', redirectTo: 'app/dashboard' },
  {
    path: 'login',
    canActivate: [guestGuard],
    loadComponent: () => import('./features/login/login.component').then((m) => m.LoginComponent)
  },
  {
    path: 'register',
    canActivate: [guestGuard],
    loadComponent: () => import('./features/register/register.component').then((m) => m.RegisterComponent)
  },
  {
    path: 'app',
    canActivate: [authGuard],
    loadComponent: () => import('./features/shell/shell.component').then((m) => m.ShellComponent),
    children: [
      { path: '', pathMatch: 'full', redirectTo: 'dashboard' },
      {
        path: 'dashboard',
        loadComponent: () => import('./features/dashboard/dashboard.component').then((m) => m.DashboardComponent)
      },
      {
        path: 'book',
        loadComponent: () => import('./features/book-ride/book-ride.component').then((m) => m.BookRideComponent)
      },
      {
        path: 'trips',
        loadComponent: () => import('./features/trips/trips.component').then((m) => m.TripsComponent)
      },
      {
        path: 'drivers',
        loadComponent: () => import('./features/drivers/drivers.component').then((m) => m.DriversComponent)
      },
      {
        path: 'admin',
        canActivate: [adminGuard],
        loadComponent: () => import('./features/admin/admin.component').then((m) => m.AdminComponent)
      }
    ]
  },
  { path: '**', redirectTo: 'app/dashboard' }
];
