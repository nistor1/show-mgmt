import {UserModel} from "./user.model";
import {ShowModel} from "./show.model";

export interface CommentModel {
  commentId: string;
  user: UserModel;
  show: ShowModel;
  description: string;
  commentDate: string;
}
