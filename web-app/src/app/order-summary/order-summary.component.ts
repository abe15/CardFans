import { Component } from '@angular/core';
import { OrderGetResponse } from '../models/order-get-response.model';
import { RestDataSource } from '../services/rest.datasource';
import { UserService } from '../services/user/user-service.service';

@Component({
  selector: 'app-order-summary',
  templateUrl: './order-summary.component.html',
  styleUrls: ['./order-summary.component.css'],
})
export class OrderSummaryComponent {
  public orders: OrderGetResponse[] = [];
  constructor(
    private restDataSource: RestDataSource,
    private userService: UserService
  ) {
    this.restDataSource
      .getUserOrders(userService.currentUser?.userId ?? 1)
      .subscribe((response) => {
        console.log(response);
        this.orders = response;
      });
  }
}
