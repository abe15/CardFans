import { OrderItemsWithProduct } from './order-items-product-info.model';

export interface OrderGetResponse {
  orderId: number;
  items: OrderItemsWithProduct[];
  total: number;
  firstName: string;
  lastName: string;
  email: string;
  phoneNumber: string;
  address1: string;
  address2: string;
  city: string;
  state: string;
  zipCode: string;
  country: string;
}
