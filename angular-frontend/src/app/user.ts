import {Role} from "./role";

export class User {
  id: number;
  login: string
  password: string
  firstName: string;
  lastName: string;
  birthday: string
  email: string;
  role: Role;
}
