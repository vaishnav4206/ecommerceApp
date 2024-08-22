import { Component } from '@angular/core';
import { CartItem } from '../../../model/cartItem';
import { CartService } from '../../../services/cart/cart.service';

@Component({
  selector: 'app-cart',
  templateUrl: './cart.component.html',
  styleUrl: './cart.component.scss'
})
export class CartComponent {

  cartItems: CartItem[] = [];
  userId: string | null = sessionStorage.getItem('userId');
  // totalAmount: number = 0;
  // TO DO: ui responsiveness 

  constructor(private cartService: CartService) {}

  ngOnInit(): void {
    this.loadCartItems();
  }

  loadCartItems(): void {
    if(this.userId != null){
      console.log("inside userid not null condition")
      this.cartService.getCartItems(this.userId).subscribe((data: CartItem[]) => {
        this.cartItems = data;
      });
    }
    else {
      console.log("user id is null")
    }
    
  }

  // updateQuantity(cartItem: CartItem, newQuantity: number): void {
  //   cartItem.quantity = newQuantity;
  //   this.cartService.updateCartItem(cartItem).subscribe(() => {
  //     console.log(`${cartItem.productName} quantity updated to ${newQuantity}`);
  //   });
  // }

  updateQuantity(cartItem: CartItem, newQuantity: number): void {
    cartItem.quantity = newQuantity;
    this.cartService.updateCartItemQuantity(cartItem).subscribe(() => {
      console.log(`${cartItem.productName} quantity updated to ${newQuantity}`);
    });
  }

  removeItem(id?: number): void {
    if (id !== undefined) {
      this.cartService.removeCartItem(id).subscribe(() => {
        this.cartItems = this.cartItems.filter((item) => item.id !== id);
        console.log(`Item removed from cart`);
      });
    }
    
  }

  calculateTotal(): number {
    return this.cartItems.reduce((total, item) => total + (item.price * item.quantity), 0);
  }
  
  proceedToBuy(): void {
    console.log('Proceeding to buy');
  }
  

//   increaseQuantity(item: CartItem): void {
//     item.quantity++;
//     this.updateQuantity(item, item.quantity);
// }

// decreaseQuantity(item: CartItem): void {
//     if (item.quantity > 1) {
//         item.quantity--;
//         this.updateQuantity(item, item.quantity);
//     }
// }


}
