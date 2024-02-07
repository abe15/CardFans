import { Component } from '@angular/core';
import { Product } from 'src/app/models/product.model';
import { RestDataSource } from 'src/app/services/rest.datasource';
import { PRODUCTS } from '../../models/mock.products';

@Component({
  selector: 'app-product-page',
  templateUrl: './product-page.component.html',
  styleUrls: ['./product-page.component.css']
})
export class ProductPageComponent {
  allProducts: Product[] = [];

  constructor(private restDataSource: RestDataSource) {
    this.restDataSource.getProducts().subscribe((x) => {
      this.allProducts = x;
    });
  }
}
