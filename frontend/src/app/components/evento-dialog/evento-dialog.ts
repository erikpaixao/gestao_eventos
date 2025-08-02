import { CommonModule } from '@angular/common';
import { Component, Inject, OnInit } from '@angular/core';
import {
  FormBuilder,
  FormGroup,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { Evento } from '../../models/evento.model';
import { MATERIAL_MODULES } from '../../shared/material';

@Component({
  selector: 'app-event-dialog',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, ...MATERIAL_MODULES],
  templateUrl: './evento-dialog.html',
  styleUrl: './evento-dialog.css',
})
export class EventDialogComponent implements OnInit {
  form: FormGroup;
  minDate: Date = new Date();

  constructor(
    private fb: FormBuilder,
    public dialogRef: MatDialogRef<EventDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: Evento | null // null para novo evento
  ) {
    this.form = this.fb.group({
      titulo: [
        data?.titulo || '',
        [
          Validators.required,
          Validators.minLength(3),
          Validators.maxLength(100),
        ],
      ],
      descricao: [
        data?.descricao || '',
        [
          Validators.required,
          Validators.minLength(10),
          Validators.maxLength(1000),
        ],
      ],
      local: [
        data?.local || '',
        [
          Validators.required,
          Validators.minLength(3),
          Validators.maxLength(200),
        ],
      ],
      dataEvento: [data?.dataEvento || new Date()],
    });
  }

  ngOnInit(): void {}

  save(): void {
    if (this.form.valid) {
      const evento: Evento = {
        id: this.data?.id ?? 0,
        ...this.form.value,
      };
      this.dialogRef.close(evento); // retorna o evento para quem abriu o dialog
    }
  }

  cancel(): void {
    this.dialogRef.close();
  }
}
