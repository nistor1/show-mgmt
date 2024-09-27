import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ShowModel } from '../../../shared/models/show.model';
import {CommentModel} from "../../../shared/models/comment.model";


@Injectable({
  providedIn: 'root'
})
export class CommentService {

  constructor(private http: HttpClient) {
  }

  getById(id: string): Observable<CommentModel> {
    return this.http.get<CommentModel>(`comment/v1/${id}`);
  }

  getAll(): Observable<CommentModel[]> {
    return this.http.get<CommentModel[]>('comment/v1', {});
  }
  getAllByShow(id: string): Observable<CommentModel[]> {
    console.log('all-from-show in service: ' + id);
    return this.http.get<CommentModel[]>(`comment/v1/all-from-show/${id}`);
  }
  deleteById(id: string): Observable<CommentModel> {
    return this.http.delete<CommentModel>(`comment/v1/${id}`);
  }
  save(comment: CommentModel): Observable<CommentModel> {
    return this.http.post<CommentModel>('comment/v1', comment);
  }
  updateShow(comment: CommentModel): Observable<CommentModel> {
    return this.http.put<CommentModel>('comment/v1', comment);
  }
}
