import { Product } from './product.model';

export interface OrderItemsWithProduct {
  orderItemId: number;
  quantity: number;
  product: Product;
}
