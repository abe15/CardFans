import { Component, OnInit, OnDestroy } from '@angular/core';

import { Router } from '@angular/router';

import { FormControl, FormGroup, Validators } from '@angular/forms';
import { delay, takeWhile } from 'rxjs';
import { AuthService } from 'src/app/services/authentication/auth.service';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { UserService } from 'src/app/services/user/user-service.service';
import { RegisterComponent } from '../register/register.component';

@Component({
  selector: 'login-form',

  templateUrl: './login.component.html',
})
export class LoginComponent implements OnInit, OnDestroy {
  username: string = '';
  password: string = '';
  errorMessage: string = '';

  //next page after successful login'
  //defualt profile
  public nextRoute: string = '/profile';

  isComponentAlive = false;

  loginForm = new FormGroup({
    username: new FormControl('', {
      validators: Validators.required,
      updateOn: 'change',
    }),
    password: new FormControl('', {
      validators: Validators.required,
      updateOn: 'change',
    }),
  });

  constructor(
    private router: Router,
    private authServ: AuthService,
    public activeModal: NgbActiveModal,
    private uServ: UserService,
    private modalService: NgbModal
  ) {
    this.isComponentAlive = true;
  }

  //subscribe to form input changes
  ngOnInit(): void {
    this.loginForm
      .get('username')
      ?.valueChanges.pipe(takeWhile(() => this.isComponentAlive))
      .subscribe(() => {
        this.loginForm.get('username')?.updateValueAndValidity();
      });
  }

  ngOnDestroy(): void {
    this.isComponentAlive = false;
  }

  openRegister() {
    this.modalService.dismissAll();
    this.modalService.open(RegisterComponent);
  }
  formSubmit(form: FormGroup): void {
    if (form.valid) {
      const { username, password } = form.value;

      //attempt to log in ser
      this.authServ
        .authenticate(username ?? '', password ?? '')
        .subscribe((response) => {
          //if successful, response is User object
          if (response) {
            //save and goto profile component
            this.uServ.saveUserDetails(response);
            this.router.navigateByUrl(this.nextRoute);
            //close log in popup
            this.activeModal.close();
          } else {
            //log in failure
            this.errorMessage = 'User name or password is incorrect';
          }
        });
    } else {
      //form invalid
      this.errorMessage = 'Please enter username and password';
    }
  }
}
