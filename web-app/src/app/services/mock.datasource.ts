import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { catchError, Observable, of, throwError } from 'rxjs';
import { Product } from '../models/product.model';
import { User } from '../models/user.model';
import { LoginRequest } from '../models/login-request.model';
import { USERS } from '../models/mock.users';

/*Class to mock http requests until we create a legit one */
@Injectable()
export class MockDataSource {
  auth_token?: string;
  mockUsers: User[]; //Registered users

  constructor() {
    this.mockUsers = USERS;
    console.log('using Mock Rest Data Source');
  }

  //Look for mathcing username/password in mockUsers array
  //return true if match found else false
  authenticate(login: {
    username: string;
    password: string;
  }): Observable<User> {
    //look for username is array of users
    let i = -1;
    this.mockUsers.forEach((user, index) => {
      if (user.username == login.username && user.password == login.password) {
        i = index;
        console.log(login);
        console.log('Found:' + user);
      }
    });

    return of(this.mockUsers[i]).pipe(
      catchError((err) => this.handleError(err))
    );
  }

  //Update userinfo
  update(user: User): Observable<User> {
    let index = this.mockUsers.findIndex(
      (element) => element.userId == user.userId
    );
    if (index) this.mockUsers[index] = user;
    console.log(user);
    return of(user);
  }

  //Update userinfo to database
  register(user: User): Observable<User> {
    this.mockUsers.push(user);
    return of(user);
  }
  //Throw error for any request failures
  private handleError(res: HttpErrorResponse | any) {
    return throwError(() => new Error('Error in mock data service'));
  }
}
