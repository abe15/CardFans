import { Injectable } from '@angular/core';
import { catchError, map, Observable, of } from 'rxjs';
import { User } from 'src/app/models/user.model';
import { AuthService } from '../authentication/auth.service';
import { RestDataSource } from '../rest.datasource';

@Injectable({
  providedIn: 'root',
})
export class UserService {
  constructor(
    private authServ: AuthService,
    private datasource: RestDataSource
  ) {
    //Check to see if existing user
    this.initializeUserDetailsIfExists();

    //console.log('in user service constructor');
    //subscribe to log in and log out events
    this.authServ.authenticated.subscribe((isAuthenticated) => {
      //get new or current user details
      if (isAuthenticated) {
        this.initializeUserDetailsIfExists();
      } else {
        this.logout();
      }
    });
  }

  //if authenticated==true then
  //currentUser contains user info
  currentUser?: User | undefined;

  //updates user info to backend database
  updateUserInfo(user: User): Observable<boolean> {
    return this.datasource.update(user).pipe(
      map((response) => {
        if (response) {
          this.saveUserDetails(response);
          return true;
        } else {
          return false;
        }
      }),
      catchError((err) => {
        return of(false);
      })
    );
  }

  //updates user info to backend database
  registerUser(user: User): Observable<boolean> {
    return this.datasource.register(user).pipe(
      map((response) => {
        if (response) {
          return true;
        } else {
          return false;
        }
      }),
      catchError((err) => {
        return of(false);
      })
    );
  }

  //Updates user details to localStorage
  saveUserDetails(user: User) {
    // store user details in local storage
    localStorage.setItem('currentUser', JSON.stringify(user || '{}'));
    this.currentUser = user;
    console.log('user saved');
  }

  initializeUserDetailsIfExists() {
    let temp = localStorage.getItem('currentUser');
    if (temp) {
      this.currentUser = JSON.parse(temp);
    }
  }

  removeUserDetails() {
    console.log('removing currentuser');
    localStorage.removeItem('currentUser');
    localStorage.removeItem('jwtToken');
  }

  //Log out user by deleting user data in localStorage
  logout() {
    localStorage.removeItem('jwtToken');
    localStorage.removeItem('currentUser');
  }
}
