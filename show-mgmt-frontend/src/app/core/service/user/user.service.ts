import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { UserModel } from '../../../shared/models/user.model';
import {ShowModel} from "../../../shared/models/show.model";


@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private http: HttpClient) {
  }

  getInfo(): Observable<UserModel> {
    return this.http.get<UserModel>('user/v1/info');
  }

  getById(id: string): Observable<UserModel> {
    return this.http.get<UserModel>(`user/v1/${id}`);
  }

  getAll(rating: number = 0): Observable<UserModel[]> {
    return this.http.get<UserModel[]>('user/v1', {
      params: {
        rating
      }
    });
  }
  deleteById(id: string): Observable<UserModel> {
    return this.http.delete<UserModel>(`user/v1/${id}`);
  }

  createUser(user: UserModel): Observable<UserModel> {
    return this.http.post<UserModel>('user/v1', user);
  }
  updateUser(user: UserModel): Observable<UserModel> {
    return this.http.put<UserModel>('user/v1', user);
  }
}
