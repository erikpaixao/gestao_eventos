import { AsyncPipe, NgIf } from '@angular/common';
import { Component, signal } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { NgToastComponent, TOAST_POSITIONS } from 'ng-angular-popup';
import { Loader } from './components/loader/loader';
import { LoaderService } from './services/loader/loader';

@Component({
  selector: 'app-root',
  imports: [RouterOutlet, Loader, NgIf, AsyncPipe, NgToastComponent],
  templateUrl: './app.html',
  styleUrl: './app.css',
})
export class App {
  protected readonly title = signal('gestao-eventos');

  loading: typeof this.loaderService.loading$;
  TOAST_POSITIONS = TOAST_POSITIONS;

  constructor(private loaderService: LoaderService) {
    this.loading = this.loaderService.loading$;
  }
}
