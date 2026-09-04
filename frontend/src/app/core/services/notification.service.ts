import { Injectable, inject, signal } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { AppNotification, ToastMessage } from '../models/driver.model';

@Injectable({ providedIn: 'root' })
export class NotificationService {
  private readonly http = inject(HttpClient);
  private nextToastId = 1;

  readonly toasts = signal<ToastMessage[]>([]);

  list() {
    return this.http.get<AppNotification[]>('/api/notifications');
  }

  pushToast(title: string, body: string, tone: ToastMessage['tone'] = 'info'): void {
    const toast: ToastMessage = { id: this.nextToastId++, title, body, tone };
    this.toasts.update((items) => [toast, ...items].slice(0, 4));
    setTimeout(() => this.dismiss(toast.id), 5200);
  }

  dismiss(id: number): void {
    this.toasts.update((items) => items.filter((item) => item.id !== id));
  }
}
