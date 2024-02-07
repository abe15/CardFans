import { Component } from '@angular/core';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { User } from 'src/app/models/user.model';
import { CartService } from 'src/app/services/cart/cart.service';
import { UserService } from 'src/app/services/user/user-service.service';
import { CartEntry } from '../../models/cartentry.model';
import { LoginComponent } from '../login/login.component';

@Component({
  selector: 'app-cart',
  templateUrl: './cart.component.html',
  styleUrls: ['./cart.component.css'],
})
export class CartComponent {
  cart: CartEntry[];
  currentUser?: User;

  constructor(
    private cartService: CartService,
    private userService: UserService,
    private modalService: NgbModal
  ) {
    this.cart = this.cartService.Cart;
    this.currentUser = userService.currentUser ?? undefined;
  }

  // cart: CartEntry[] = [
  //   {prodId: 1, name: 'aviary', image: 'aviary.jpg', quantity: 3, price: 5.99, total: 17.97},
  //   {prodId: 3, name: 'Dark Mode', image: 'darkMode.jpg', quantity: 4, price: 6.99, total: 27.95},
  //   {prodId: 9, name: 'Avengers', image: 'avengers.jpg', quantity: 2, price: 7.99, total: 15.98}];

  removeItem(index: number): void {
    this.cart = this.cartService.removeFromCart(index);
    this.cartService.Cart = this.cart;
  }

  getGrandTotal(): number {
    let sum: number = 0;
    for (let entry of this.cart) {
      sum += entry.total;
    }
    return sum;
  }

  updateTotal(index: number): void {
    this.cart[index].total = Number(
      (this.cart[index].quantity * this.cart[index].price).toFixed(2)
    );
    this.cartService.Cart = this.cart;
  }

  openLogin() {
    const modalRef = this.modalService.open(LoginComponent);
    modalRef.componentInstance.name = 'Login';
    modalRef.componentInstance.nextRoute = '/checkout';
  }
}
