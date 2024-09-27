import {UserModel} from "./user.model";
import {ShowModel} from "./show.model";

export interface OrderModel {
  orderId: string;
  user: UserModel;
  show: ShowModel;
  orderDate: string;
  numberOfTicketsToBuy: number;
}
