import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Evento } from '../../models/evento.model';

@Injectable({
  providedIn: 'root',
})
export class EventoService {
  private apiUrl = 'http://localhost:8080/api/eventos';

  constructor(private http: HttpClient) {}

  getAll(page: number, count: number): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}?page=${page}&count=${count}`);
  }

  getById(id: number): Observable<Evento> {
    return this.http.get<Evento>(`${this.apiUrl}/${id}`);
  }

  create(event: Partial<Evento>): Observable<Evento> {
    return this.http.post<Evento>(this.apiUrl, event);
  }

  update(id: number, event: Partial<Evento>): Observable<Evento> {
    return this.http.put<Evento>(`${this.apiUrl}/${id}`, event);
  }

  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }
}
