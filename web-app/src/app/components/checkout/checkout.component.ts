import { Component } from '@angular/core';
import { CartService } from 'src/app/services/cart/cart.service';
import { OrderEntry } from 'src/app/models/order-entry.model';
import { Order } from 'src/app/models/order.model';
import { CartEntry } from '../../models/cartentry.model';
import { User } from 'src/app/models/user.model';
import { UserService } from 'src/app/services/user/user-service.service';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { RestDataSource } from 'src/app/services/rest.datasource';
import { Router } from '@angular/router';

@Component({
  selector: 'app-checkout',
  templateUrl: './checkout.component.html',
  styleUrls: ['./checkout.component.css'],
})
export class CheckoutComponent {
  /**
   * add authentication to check out button - redirect login back to cart?
   * submit order to backend
   */
  cart: CartEntry[] = [];
  subtotal: number = 0;
  shipping = 3.87;
  grandTotal: number = 0;
  user?: User;
  shippingForm: FormGroup;
  formInvalid = false;

  constructor(
    private cartService: CartService,
    private userService: UserService,
    private restDataSource: RestDataSource,
    private router: Router
  ) {
    this.cart = this.cartService.Cart;

    for (let entry of this.cart) {
      this.subtotal += entry.total;
    }
    this.grandTotal = this.subtotal + this.shipping;

    this.user = userService.currentUser;

    this.shippingForm = new FormGroup({
      firstName: new FormControl(this.user?.firstName ?? '', {
        validators: Validators.required,
      }),
      lastName: new FormControl(this.user?.lastName ?? '', {
        validators: Validators.required,
      }),
      email: new FormControl(this.user?.email ?? '', {
        validators: Validators.required,
      }),
      phoneNumber: new FormControl(this.user?.phoneNumber ?? '', {
        validators: Validators.required,
      }),
      address1: new FormControl(this.user?.addressLine1 ?? '', {
        validators: Validators.required,
      }),
      address2: new FormControl(this.user?.addressLine2 ?? ''),
      city: new FormControl(this.user?.city ?? '', {
        validators: Validators.required,
      }),
      state: new FormControl(this.user?.state ?? '', {
        validators: Validators.required,
      }),
      zipCode: new FormControl(this.user?.zipCode ?? '', {
        validators: Validators.required,
      }),
      country: new FormControl(this.user?.country ?? '', {
        validators: Validators.required,
      }),
    });
  }

  createOrder() {
    let orderEntry: OrderEntry[] = [];

    this.cartService.Cart.forEach((entry) => {
      //orderEntry.push(entry);
    });

    // let order: Order = new Order(orderEntry);
  }

  paypalButton(element: any) {
    let orderButton = document.getElementById('order-btn');

    element.textContent = 'Having you pay through Paypal...';
    setTimeout(() => {
      element.textContent = 'Payment processed!';
      element.className = 'btn btn-success';
      orderButton?.removeAttribute('disabled');
    }, 1000);
  }

  submitForm(sForm: FormGroup) {
    if (sForm.invalid) {
      this.formInvalid = true;
      Object.keys(sForm.controls).forEach((field) => {
        if (sForm.controls[field].invalid) {
          console.log('Invalid field: ' + field);
          document
            .getElementById(field + '-label')
            ?.setAttribute('style', 'color:red');
        }
      });
    } else {
      console.log('Form valid');

      let orderEntry: OrderEntry[] = [];

      this.cartService.Cart.forEach((entry) => {
        orderEntry.push({ productId: entry.prodId, quantity: entry.quantity });
      });

      let order: Order = new Order(
        orderEntry,
        sForm.getRawValue(),
        this.grandTotal,
        1
      );

      this.restDataSource.postOrder(order).subscribe((response) => {
        //if successful, response is User object
        if (response) {
          localStorage.setItem('orderId', '' + response.orderId);
          this.cartService.Cart = [];
          this.router.navigate(['/confirmation']);

          //alert(`Order created successfully id: ${ response.orderId }.`);
          //sometext
        } else {
          alert('Order could not go through, please try again.');
          //else
          //give an error on te form
        }
      });
    }
  }
}
