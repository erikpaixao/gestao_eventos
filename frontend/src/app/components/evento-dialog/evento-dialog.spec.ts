import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EventoDialog } from './evento-dialog';

describe('EventoDialog', () => {
  let component: EventoDialog;
  let fixture: ComponentFixture<EventoDialog>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [EventoDialog]
    })
    .compileComponents();

    fixture = TestBed.createComponent(EventoDialog);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
