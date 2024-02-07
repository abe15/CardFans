import { Injectable } from '@angular/core';
import {
  BehaviorSubject,
  Observable,
  pipe,
  of,
  Subject,
  windowTime,
  map,
  catchError,
} from 'rxjs';
import { User } from 'src/app/models/user.model';
import { RestDataSource } from '../rest.datasource';
import { UserService } from '../user/user-service.service';

@Injectable()
export class AuthService {
  //subscribe to authenticated for log in/out notifications
  private authenticatedSubject: BehaviorSubject<boolean>;
  public authenticated: Observable<boolean>;

  //Inject Rest Data source to issue HTTP authentication requests
  constructor(private datasource: RestDataSource) {
    //check if uer already authenticated
    let firstValue: boolean = localStorage.getItem('currentUser')
      ? true
      : false;
    //broadcast authencation status
    this.authenticatedSubject = new BehaviorSubject<any>(firstValue);
    this.authenticated = this.authenticatedSubject.asObservable();
  }

  //Returns user if credentials valid
  //else returns undefined
  authenticate(
    username: string,
    password: string
  ): Observable<User | undefined> {
    return this.datasource.authenticate({ username, password }).pipe(
      map((user: User) => {
        //if user.props undefined throw err
        if (undefined === user.email) {
          throw 'err';
        }
        this.authenticatedSubject.next(true);
        return user;
      }),
      catchError((err) => {
        console.log(err);
        //invalid user credentials
        //console.log(err);
        return of(undefined);
      })
    );
  }

  //Check if user is authenticated
  //not in use
  get isAuthenticated(): boolean {
    return true;
  }

  //Log out user
  //notify subscribers
  logout() {
    console.log('in logout');
    this.authenticatedSubject.next(false);
  }
}
