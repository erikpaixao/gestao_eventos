import { CommonModule } from '@angular/common';
import { Component, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { MATERIAL_MODULES } from '../../shared/material';

@Component({
  selector: 'app-alert-dialog',
  standalone: true,
  imports: [CommonModule, ...MATERIAL_MODULES],
  templateUrl: './alert-dialog.html',
})
export class AlertDialogComponent {
  constructor(
    public dialogRef: MatDialogRef<AlertDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: { message: string }
  ) {}

  close(result: boolean): void {
    this.dialogRef.close(result);
  }
}
