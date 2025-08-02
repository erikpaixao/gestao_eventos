import { Routes } from '@angular/router';
import { EventComponent } from './pages/evento/evento';
import { Home } from './pages/home/home';

export const routes: Routes = [
  { path: '', component: Home },
  { path: 'evento', component: EventComponent },
  { path: 'evento/:id', component: EventComponent },
];
