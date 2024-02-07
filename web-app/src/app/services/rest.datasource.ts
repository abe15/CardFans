import { Injectable } from '@angular/core';
import {
  HttpClient,
  HttpErrorResponse,
  HttpHeaders,
} from '@angular/common/http';
import { catchError, map, Observable, of, tap, throwError } from 'rxjs';
import { Product } from '../models/product.model';
import { User } from '../models/user.model';
import { LoginRequest } from '../models/login-request.model';
import { AuthResponse } from '../models/auth-response.model';
import { Order } from '../models/order.model';
import { OrderCreateResponse } from '../models/order-response.model';
import { OrderGetResponse } from '../models/order-get-response.model';
//import { HttpHeaders } from '@angular/common/http';

const PROTOCOL = 'http';
const PORT = 8080;

@Injectable()
export class RestDataSource {
  baseUrl: string; //location of our Rest API
  auth_token?: string; //Maybe store jwt here for later
  headers_object: HttpHeaders; // = new HttpHeaders();
  //HttpClient used for http requests similar to postman
  constructor(private http: HttpClient) {
    this.baseUrl = `${PROTOCOL}://${location.hostname}:${PORT}/api/v1/`;
    this.baseUrl = 'http://localhost:8080/api/v1/'; //same as above
    this.headers_object = new HttpHeaders().set(
      'Authorization',
      'Bearer ' + (localStorage.getItem('jwtToken') ?? '')
    );
  }

  //Register userinfo to database
  register(user: User): Observable<User> {
    return (
      this.http
        // http://localhost:8080/api/v1/auth/signup
        .post<User>(this.baseUrl + 'auth/signup', user, {})
        .pipe(catchError((err) => this.handleError(err)))
    );
  }

  //Check if valid username password combo
  //If succesful, returns user object
  //else throws error
  authenticate(login: {
    username: string;
    password: string;
  }): Observable<User> {
    return (
      this.http
        // http://localhost:8080/api/v1/auth/signin
        .post<AuthResponse>(this.baseUrl + 'auth/signin', login, {})
        .pipe(
          map((authResponse: AuthResponse) => {
            localStorage.setItem('jwtToken', authResponse.accessToken);
            this.auth_token = authResponse.accessToken;
            this.headers_object = new HttpHeaders().set(
              'Authorization',
              'Bearer ' + (authResponse.accessToken ?? '')
            );
            return authResponse.user;
          }),
          catchError((err) => this.handleError(err))
        )
    );
  }
  //Update userinfo to database
  update(user: User): Observable<User> {
    return (
      this.http
        // http://localhost:8080/api/v1/users
        .patch<User>(this.baseUrl + 'users', user, {
          headers: this.headers_object,
        })
      //.pipe(catchError((err) => this.handleError(err)))
    );
  }

  getProducts(): Observable<Product[]> {
    return (
      this.http
        // http://localhost:8080/api/v1/products
        .get<Product[]>(this.baseUrl + 'products')
        .pipe(catchError((err) => this.handleError(err)))
    );
  }

  getProductByName(productName: String): Observable<Product> {
    productName = productName.replace(/\s/g, '+');
    return (
      this.http
        // http://localhost:8080/api/v1/products/{name}
        .get<Product>(this.baseUrl + 'products/' + productName)
        .pipe(catchError((err) => this.handleError(err)))
    );
  }
  postOrder(order: Order): Observable<OrderCreateResponse> {
    return (
      this.http
        // http://localhost:8080/api/v1/orders
        .post<OrderCreateResponse>(this.baseUrl + 'orders', order, {
          headers: this.headers_object,
        })
        .pipe(catchError((err) => this.handleError(err)))
    );
  }

  getUserOrders(userId: number): Observable<OrderGetResponse[]> {
    return (
      this.http
        // http://localhost:8080/api/v1/users/{userId}/orders
        .get<OrderGetResponse[]>(this.baseUrl + 'users/' + userId + '/orders', {
          headers: this.headers_object,
        })
        .pipe(catchError((err) => this.handleError(err)))
    );
  }
  /*

  saveOrder(): Observable<Order> {
    return (
      this.http
        // http://localhost:8080/api/v1/orders
        .post<Order>(this.baseUrl + 'orders',{})
        .pipe(catchError((err) => this.handleError(err)))
    );
  }
*/
  //Throw error for any http request failures
  private handleError(res: HttpErrorResponse | any) {
    return throwError(() => new Error('Error is data service'));
  }
}
