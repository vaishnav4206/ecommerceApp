import { Component, OnInit } from '@angular/core';
import { Product } from '../../model/product';
import { ProductService } from '../../services/products/product.service';
import { CartService } from '../../services/cart/cart.service';
import { CartItem } from '../../model/cartItem';

@Component({
  selector: 'app-product',
  templateUrl: './product.component.html',
  styleUrl: './product.component.scss'
})
export class ProductComponent implements OnInit {

  products: Product[] = [];

  constructor(
    private productService: ProductService,
    private cartService: CartService
  ) { }

  ngOnInit(): void {
    this.productService.getProducts().subscribe(data => {
      this.products = data;
    });
  }

  addToCart(product: Product): void {
    const userId = sessionStorage.getItem('userId'); 
    if(userId == null){
      console.error("user id is null");
      return;
    }
    const cartItem: CartItem = {
      userId: userId,
      productId: product.id,
      productName: product.name,
      quantity: 1,
      price: product.price,
    };

    this.cartService.addCartItem(cartItem).subscribe(() => {
      console.log(`${product.name} added to cart!`);
    });
  }

}
