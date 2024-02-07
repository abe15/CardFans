import { Component } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-confirmation',
  templateUrl: './confirmation.component.html',
  styleUrls: ['./confirmation.component.css'],
})
export class ConfirmationComponent {
  orderId: string = '';
  constructor(private router: Router) {
    this.orderId = localStorage.getItem('orderId') ?? '';
  }

  //Last lifecycle hook after initializing
  ngAfterViewChecked() {
    setTimeout(() => {
      this.router.navigate(['/home']);
    }, 3500);
  }
}
