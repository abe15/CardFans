import { Component, OnInit, OnDestroy } from '@angular/core';

import { Router } from '@angular/router';

import { FormControl, FormGroup, Validators } from '@angular/forms';
import { catchError, takeWhile } from 'rxjs';
import { AuthService } from 'src/app/services/authentication/auth.service';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { LoginComponent } from '../login/login.component';
import { User } from 'src/app/models/user.model';
import { UserService } from 'src/app/services/user/user-service.service';
@Component({
  selector: 'register-form',
  styleUrls: ['./register.component.css'],
  templateUrl: './register.component.html',
})
export class RegisterComponent implements OnInit, OnDestroy {
  errorMessage: string = '';

  isComponentAlive = false;

  profileForm: FormGroup;
  constructor(
    private router: Router,
    private authServ: AuthService,
    public activeModal: NgbActiveModal,
    private uServ: UserService,
    private modalService: NgbModal
  ) {
    this.profileForm = new FormGroup({
      firstName: new FormControl('', {
        validators: Validators.required,
        updateOn: 'change',
      }),
      lastName: new FormControl('', {
        validators: Validators.required,
        updateOn: 'change',
      }),
      addressLine1: new FormControl('', {
        validators: Validators.required,
        updateOn: 'change',
      }),
      city: new FormControl('', {
        validators: Validators.required,
        updateOn: 'change',
      }),
      zipCode: new FormControl('', {
        validators: Validators.required,
        updateOn: 'change',
      }),
      state: new FormControl('', {
        validators: Validators.required,
        updateOn: 'change',
      }),
      email: new FormControl('', {
        validators: Validators.required,
        updateOn: 'change',
      }),
      password: new FormControl('', {
        validators: Validators.required,
        updateOn: 'change',
      }),
      username: new FormControl('', {
        validators: Validators.required,
        updateOn: 'change',
      }),
    });
  }
  ngOnInit(): void {}

  ngOnDestroy(): void {
    this.isComponentAlive = false;
  }
  openLogin() {
    this.modalService.dismissAll();
    this.modalService.open(LoginComponent);
  }
  formSubmit(form: FormGroup): void {
    if (form.valid) {
      let user: User = { ...form.value };

      //Use user service to send
      //put request to database
      this.uServ
        .registerUser(user)
        .pipe(
          catchError((err) => {
            console.log(err);
            //some error dont know
            return err;
          })
        )
        .subscribe((succesful) => {
          if (succesful) {
            this.activeModal.close();
          } else {
            //false
            //need to display error to try again
          }
        });
    } else {
      this.errorMessage = 'Form Data Invalid';
    }
  }
}
