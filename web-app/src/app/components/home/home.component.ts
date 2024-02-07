import { Component } from '@angular/core';
import { PRODUCTS } from 'src/app/models/mock.products';
import { Product } from 'src/app/models/product.model';
import { RestDataSource } from 'src/app/services/rest.datasource';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css'],
})
export class HomeComponent {
  featuredProducts: Product[] = [];

  constructor(private restDataSource: RestDataSource) {
    this.restDataSource.getProductByName('Yellow Submarine').subscribe((x) => {
      this.featuredProducts.push(x);
      console.log(x);
    });
    this.restDataSource.getProductByName('Koi V2').subscribe((x) => {
      this.featuredProducts.push(x);
      console.log(x);
    });
    this.restDataSource.getProductByName('8 Bit Red').subscribe((x) => {
      this.featuredProducts.push(x);
      console.log(x);
    });
  }
}
