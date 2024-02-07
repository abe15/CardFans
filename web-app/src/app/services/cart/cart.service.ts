import { Injectable } from '@angular/core';
import { withInMemoryScrolling } from '@angular/router';
import { CartEntry } from 'src/app/models/cartentry.model';
import { PRODUCTS } from 'src/app/models/mock.products';

@Injectable({
  providedIn: 'root',
})
export class CartService {
  private cart$: CartEntry[] = [];

  get Cart() {
    return this.cart$;
  }

  set Cart(newCart: CartEntry[]) {
    this.cart$ = newCart.slice(0);
  }
  constructor() {}

  addToCart(prod: CartEntry): void {
    //check if the product is already in the cart
    //only checks if the cart isn't empty
    console.log(prod);
    if (this.cart$.length != 0) {
      for (let entry of this.cart$) {
        //updates quantity and total when it finds a product with the same id
        if (entry.prodId == prod.prodId) {
          entry.quantity += 1;
          entry.total = entry.price * entry.quantity;
          return;
        }
      }
    }
    //push product into cart
    this.cart$.push(prod);
  }

  removeFromCart(index: number): CartEntry[] {
    this.cart$.splice(index, 1);
    return this.cart$;
  }
}
