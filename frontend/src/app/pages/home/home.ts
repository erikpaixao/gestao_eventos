import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { PageEvent } from '@angular/material/paginator';
import { NgToastService } from 'ng-angular-popup';
import { AlertDialogComponent } from '../../components/alert-dialog/alert-dialog';
import { EventDialogComponent } from '../../components/evento-dialog/evento-dialog';
import { Evento } from '../../models/evento.model';
import { EventoService } from '../../services/evento/evento';
import { MATERIAL_MODULES } from './../../shared/material';

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
    private toast: NgToastService
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
    const dialogRef = this.dialog.open(EventDialogComponent, {
      data: null,
    });

    dialogRef.afterClosed().subscribe((result: Evento) => {
      if (result) {
        this.eventoService.create(result).subscribe({
          next: () => {
            this.openToastSucess(`Dados criados com sucesso!`);
            this.loadEvents();
          },
          error: (err) => {
            console.error('Erro ao criar evento:', err);
            this.openToastError(`Erro ao criar evento: ${err.error.erro}`);
          },
        });
      }
    });
  }

  openEditDialog(event: Evento) {
    const dialogRef = this.dialog.open(EventDialogComponent, {
      data: event,
    });

    dialogRef.afterClosed().subscribe((result: Evento) => {
      if (result) {
        this.eventoService.update(event.id, result).subscribe({
          next: () => {
            this.openToastSucess(`Dados atualizados com sucesso!`);
            this.loadEvents();
          },
          error: (err) => {
            this.openToastError(`Erro ao atualizar evento: ${err.error.erro}`);
          },
        });
      }
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
