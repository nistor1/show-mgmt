import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ShowModel } from '../../../shared/models/show.model';


@Injectable({
  providedIn: 'root'
})
export class ShowService {

  constructor(private http: HttpClient) {
  }

  getById(id: string): Observable<ShowModel> {
    return this.http.get<ShowModel>(`show/v1/${id}`);
  }

  getAll(rating: number = 0): Observable<ShowModel[]> {
    return this.http.get<ShowModel[]>('show/v1', {
      params: {
        rating
      }
    });
  }
  deleteById(id: string): Observable<ShowModel> {
    return this.http.delete<ShowModel>(`show/v1/${id}`);
  }
  createShow(show: ShowModel): Observable<ShowModel> {
    return this.http.post<ShowModel>('show/v1', show);
  }
  updateShow(show: ShowModel): Observable<ShowModel> {
    return this.http.put<ShowModel>('show/v1', show);
  }
}
