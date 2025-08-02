import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import {
  FormBuilder,
  FormGroup,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { NgToastService } from 'ng-angular-popup';
import { MATERIAL_MODULES } from '../../core/shared/material';
import { EventoService } from '../../services/evento/evento';

@Component({
  selector: 'app-event',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, ...MATERIAL_MODULES],
  templateUrl: './evento.html',
  styleUrl: './evento.css',
})
export class EventComponent implements OnInit {
  form: FormGroup;
  minDate: Date = new Date();
  isViewMode: boolean = false;
  data: any;

  constructor(
    private fb: FormBuilder,
    private eventoService: EventoService,
    private toast: NgToastService,
    private router: Router,
    private activatedrouter: ActivatedRoute
  ) {
    this.activatedrouter.queryParams.subscribe((params) => {
      this.isViewMode = params['readonly'];
      this.data = params['data'] ? JSON.parse(params['data']) : {};
    });

    this.activatedrouter.params.subscribe((params) => {
      console.log(params['id']);
    });

    this.form = this.fb.group({
      id: [this.data?.id || null],
      titulo: [
        this.data?.titulo || '',
        [
          Validators.required,
          Validators.minLength(3),
          Validators.maxLength(100),
        ],
      ],
      descricao: [
        this.data?.descricao || '',
        [
          Validators.required,
          Validators.minLength(10),
          Validators.maxLength(1000),
        ],
      ],
      local: [
        this.data?.local || '',
        [
          Validators.required,
          Validators.minLength(3),
          Validators.maxLength(200),
        ],
      ],
      dataEvento: [this.data?.dataEvento || new Date()],
    });
  }

  ngOnInit(): void {}

  salvar() {
    if (this.form.valid) {
      if (this.form.value.id) {
        this.update();
      } else {
        this.save();
      }
    }
  }

  save(): void {
    if (this.form.valid) {
      this.eventoService.create(this.form.value).subscribe({
        next: () => {
          this.openToastSucess(`Dados criados com sucesso!`);
          this.voltar();
        },
        error: (err) => {
          console.error('Erro ao criar evento:', err);
          this.openToastError(`Erro ao criar evento: ${err.error.erro}`);
        },
      });
    }
  }

  update(): void {
    if (this.form.valid) {
      this.eventoService.update(this.form.value.id, this.form.value).subscribe({
        next: () => {
          this.openToastSucess(`Dados atualizados com sucesso!`);
          this.voltar();
        },
        error: (err) => {
          this.openToastError(`Erro ao atualizar evento: ${err.error.erro}`);
        },
      });
    }
  }

  voltar(): void {
    this.router.navigate(['']);
  }

  private openToastError(message: string) {
    this.toast.danger(message, 'Erro', 10000);
  }

  private openToastSucess(message: string) {
    this.toast.success(message, 'Sucesso', 10000);
  }
}
