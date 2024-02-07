import { Component } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { catchError, map, takeWhile } from 'rxjs';
import { User } from 'src/app/models/user.model';
import { UserService } from 'src/app/services/user/user-service.service';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css'],
})
export class ProfileComponent {
  //if authenticated==true then
  //currentUser contains user info
  currentUser?: User;
  editProfile: boolean;

  profileForm: FormGroup;
  constructor(private uServ: UserService, private router: Router) {
    this.editProfile = false;
    console.log('Creating profilecomponent');
    this.currentUser = uServ.currentUser ?? undefined;

    this.profileForm = new FormGroup({
      firstName: new FormControl(this.currentUser?.firstName ?? 'user', {
        validators: Validators.required,
        updateOn: 'change',
      }),
      lastName: new FormControl(this.currentUser?.lastName ?? 'user', {
        validators: Validators.required,
        updateOn: 'change',
      }),
      addressLine1: new FormControl(this.currentUser?.addressLine1 ?? 'user', {
        validators: Validators.required,
        updateOn: 'change',
      }),
      city: new FormControl(this.currentUser?.city ?? 'user', {
        validators: Validators.required,
        updateOn: 'change',
      }),
      zipCode: new FormControl(this.currentUser?.zipCode ?? 'user', {
        validators: Validators.required,
        updateOn: 'change',
      }),
      state: new FormControl(this.currentUser?.state ?? 'user', {
        validators: Validators.required,
        updateOn: 'change',
      }),
      email: new FormControl(this.currentUser?.email ?? 'user', {
        validators: Validators.required,
        updateOn: 'change',
      }),
    });
  }

  ngOnInit(): void {}
  formSubmit(form: FormGroup): void {
    //check if form is valid
    if (form.valid && this.currentUser) {
      let user: User = { ...form.value };

      //attach userId/userName/password since
      //they are not on form
      //TODO: Implement no password
      //requirement to updat euser
      user.userId = this.currentUser.userId;
      user.username = this.currentUser.username;
      user.password = this.currentUser.password;

      //Use user service to send
      //put request to database
      this.uServ
        .updateUserInfo(user)
        .pipe(
          catchError((err) => {
            console.log(err);
            //some error dont know
            return err;
          })
        )
        .subscribe((succesful) => {
          if (succesful) {
            //update local user info
            this.currentUser = this.uServ.currentUser ?? undefined;
          } else {
            //false
            //need to display error to try again
          }
        });

      // user: User = { ...form.value };
    }
  }
  startEdit() {
    this.editProfile = true;
  }
}
