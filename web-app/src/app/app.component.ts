import { Component, Input } from '@angular/core';
import { User } from './models/user.model';
import { AuthService } from './services/authentication/auth.service';
import { UserService } from './services/user/user-service.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css'],
})
export class AppComponent {
  title = 'web-app';
  public authenticated: boolean = false;
  public currentYear = new Date().getFullYear();

  constructor(private authServ: AuthService) {}

  ngOnInit(): void {
    //subscribe to log in and log out events
    this.authServ.authenticated.subscribe((data) => {
      this.authenticated = data;
    });
  }

  resetScroll() {
    window.scroll({
      top:0,
      left:0,
      behavior: 'smooth'
    });
  }
}
