import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { PageEvent } from '@angular/material/paginator';
import { NgToastService } from 'ng-angular-popup';

import { Router } from '@angular/router';
import { AlertDialogComponent } from '../../core/components/alert-dialog/alert-dialog';
import { MATERIAL_MODULES } from '../../core/shared/material';
import { Evento } from '../../models/evento.model';
import { EventoService } from '../../services/evento/evento';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [CommonModule, ...MATERIAL_MODULES],
  templateUrl: './home.html',
  styleUrl: './home.css',
})
export class Home implements OnInit {
  displayedColumns: string[] = [
    'titulo',
    'descricao',
    'dataEvento',
    'local',
    'actions',
  ];
  events: Evento[] = [];
  totalElements = 0;
  pageSize = 5;
  pageIndex = 0;

  constructor(
    private eventoService: EventoService,
    private dialog: MatDialog,
    private toast: NgToastService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.loadEvents();
  }

  loadEvents(page: number = 0, count: number = this.pageSize): void {
    this.eventoService.getAll(page, count).subscribe({
      next: (data) => {
        this.events = data.content;
        this.totalElements = data.totalElements;
        this.pageIndex = data.number;
        this.pageSize = data.size;
      },
      error: (err) => {
        this.openToastError(`Erro ao carregar eventos: ${err.error.erro}`);
      },
    });
  }

  onPageChange(event: PageEvent): void {
    this.loadEvents(event.pageIndex, event.pageSize);
  }

  openCreateDialog() {
    this.router.navigate(['evento']);
  }

  openEditDialog(event: Evento) {
    this.router.navigate(['evento', event.id], {
      queryParams: { readonly: false, data: JSON.stringify(event) },
    });
  }

  openViewDialog(event: Evento) {
    this.router.navigate(['evento', event.id], {
      queryParams: { readonly: true, data: JSON.stringify(event) },
    });
  }

  deleteEvent(event: Evento) {
    this.openAlert(`Deseja excluir o evento "${event.titulo}"?`)
      .afterClosed()
      .subscribe((result) => {
        if (result) {
          this.eventoService.delete(event.id).subscribe({
            next: () => {
              this.openToastSucess(`Dados removidos com sucesso!`);
              this.loadEvents();
            },
            error: (err) => {
              this.openToastError(`Erro ao excluir evento: ${err.error.erro}`);
            },
          });
        }
      });
  }

  private openToastError(message: string) {
    this.toast.danger(message, 'Erro', 10000);
  }

  private openToastSucess(message: string) {
    this.toast.success(message, 'Sucesso', 10000);
  }

  private openAlert(message: string) {
    return this.dialog.open(AlertDialogComponent, {
      data: { message },
    });
  }
}
