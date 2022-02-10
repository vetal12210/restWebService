import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http'
import { Observable } from 'rxjs';
import { User } from './user';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private baseURL = "http://localhost:8080/rest/users";
  constructor(private httpClient: HttpClient) { }

    getUsersList(): Observable<User[]>{
      return this.httpClient.get<User[]>(`${this.baseURL}`);
    }

    createUser(user: User): Observable<Object>{
      return this.httpClient.post(`${this.baseURL}/create`, user);
    }

    getUserById(id: number): Observable<User>{
      return this.httpClient.get<User>(`${this.baseURL}/${id}`);
    }

    updateUser(id: number, user: User): Observable<Object>{
        return this.httpClient.put(`${this.baseURL}/edit/${id}`, user);
    }

    deleteUser(id: number): Observable<Object>{
       return this.httpClient.delete(`${this.baseURL}/delete/${id}`);
    }
}

