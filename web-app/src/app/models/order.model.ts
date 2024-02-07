import { OrderEntry } from './order-entry.model';
import { FormControl, FormGroup, Validators } from '@angular/forms';
export class Order {
  
  public items: OrderEntry[]; 
  public userId?: number;
  public firstName?: string; 
  public lastName?: string;
  public email?: string;
  public phoneNumber?: string;
  public address1?: string;
  public address2?: string;
  public city?: string;
  public state?: string;
  public zipCode?: string;
  public country?: string;
 
  public orderId?: number;
 
  public total?: number;
  
  constructor(orderEntry:OrderEntry[], shippingForm: any, total: number, userId: number
  ) {
    this.items = orderEntry; 

    this.firstName = shippingForm.firstName;
    this.lastName = shippingForm.lastName;
    this.email = shippingForm.email;
    this.phoneNumber = shippingForm.phoneNumber;
    this.address1 = shippingForm.address1;
    this.address2 = shippingForm.address2;
    this.city = shippingForm.city;
    this.state = shippingForm.state;
    this.zipCode = shippingForm.zipCode;
    this.country = shippingForm.country;

    this.total = total;
    this.userId = userId;
  }
 
   


/*
  get total(): number {
    return this.orderEntries.reduce(
      (accumulator, currentOrderEntry) => accumulator + currentOrderEntry.total,
      0
    );
  }

  get totalItems(): number {
    return this.orderEntries.reduce(
      (accumulator, currentOrderEntry) =>
        accumulator + currentOrderEntry.quantity,
      0
    );
  }*/
}
