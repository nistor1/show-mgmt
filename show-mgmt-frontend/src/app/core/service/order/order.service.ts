import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { UserModel } from '../../../shared/models/user.model';
import {ShowModel} from "../../../shared/models/show.model";
import {OrderModel} from "../../../shared/models/order.model";
import {CommentModel} from "../../../shared/models/comment.model";


@Injectable({
  providedIn: 'root'
})
export class OrderService {

  constructor(private http: HttpClient) {
  }

  getInfo(): Observable<OrderModel> {
    return this.http.get<OrderModel>('order/v1/info');
  }

  getById(id: string): Observable<OrderModel> {
    return this.http.get<OrderModel>(`order/v1/${id}`);
  }

  getAll(): Observable<OrderModel[]> {
    console.log('get all orders in service');
    return this.http.get<OrderModel[]>('order/v1');
  }
  getAllByUser(id: string): Observable<OrderModel[]> {
    console.log('all-from-show in service: ' + id);
    return this.http.get<OrderModel[]>(`order/v1/all-from-user/${id}`);
  }
  deleteById(id: string): Observable<OrderModel> {
    return this.http.delete<OrderModel>(`order/v1/${id}`);
  }
  save(orderModel: OrderModel): Observable<OrderModel> {
    return this.http.post<OrderModel>('order/v1', orderModel);
  }
}
