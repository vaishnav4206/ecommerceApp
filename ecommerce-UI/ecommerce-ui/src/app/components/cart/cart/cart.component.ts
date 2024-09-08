import { Component } from '@angular/core';
import { CartItem } from '../../../model/cartItem';
import { CartService } from '../../../services/cart/cart.service';
import { OrderService } from '../../../services/order/order.service';
import { OrderRequest } from '../../../model/order-request';
import { MatDialog } from '@angular/material/dialog';
import { OrderConfirmationDialogComponent } from '../order-confirmation-dialog/order-confirmation-dialog.component';

@Component({
  selector: 'app-cart',
  templateUrl: './cart.component.html',
  styleUrl: './cart.component.scss'
})
export class CartComponent {

  cartItems: CartItem[] = [];
  userId: string | null = sessionStorage.getItem('userId');
  totalAmount: number = 0;
  // TO DO: ui responsiveness fix

  constructor(
    private cartService: CartService,
    private orderService: OrderService,
    private dialog: MatDialog
  ) {}

  ngOnInit(): void {
    this.loadCartItems();
  }

  loadCartItems(): void {
    if(this.userId != null){
      console.log("inside userid not null condition")
      this.cartService.getCartItems(this.userId).subscribe((data: CartItem[]) => {
        this.cartItems = data;
      console.log(this.cartItems)
      });
    }
    else {
      console.log("user id is null")
    }
    
  }

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
     this.totalAmount = this.cartItems.reduce((total, item) => total + (item.price * item.quantity), 0);
     return this.totalAmount;
  }

  getProductIdsFromCartItems(): number[] {
    const productIdInCart: number[] = [];
    this.cartItems.forEach(carItem => productIdInCart.push(carItem.productId));
    return productIdInCart;
  }
  
  proceedToBuy(): void {
    const orderRequest:OrderRequest = {
      userId: this.userId,
      productIds: this.getProductIdsFromCartItems(),
      totalAmount: this.totalAmount,
      rushDelivery: this.totalAmount > 100
    }

    console.log("order req: ", orderRequest)
    this.orderService.placeOrder(orderRequest).subscribe({
      next: (data) => {
        console.log('Order placed successfully:', data);
        this.openOrderConfirmationDialog();
        this.clearCartItems();
      },
      error: (err) => {
        console.error('Error placing order:', err);
      },
      complete: () => {
        console.log('Order placement complete');
      }
    });
  }

  openOrderConfirmationDialog(): void {
    const dialogRef = this.dialog.open(OrderConfirmationDialogComponent, {
      width: '400px', 
    });

    dialogRef.afterClosed().subscribe(result => {
      console.log('The dialog was closed');
    });
  }

  clearCartItems(): void {
    if (this.userId !== null) {
      this.cartService.clearCartItems(this.userId).subscribe(() => {
        console.log('Cart items cleared successfully');
        // Optionally, you can reload the cart items to update the UI
        this.loadCartItems();
      });
    }
  }
  
}
