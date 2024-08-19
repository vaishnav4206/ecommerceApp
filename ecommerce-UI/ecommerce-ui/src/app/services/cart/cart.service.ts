import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { CartItem } from '../../model/cartItem';

@Injectable({
  providedIn: 'root',
})
export class CartService {
  private apiUrl = 'http://localhost:8081/api/cart';

  constructor(private http: HttpClient) {}

  getCartItems(userId: string): Observable<CartItem[]> {
    return this.http.get<CartItem[]>(`${this.apiUrl}/${userId}`);
  }

  addCartItem(cartItem: CartItem): Observable<CartItem> {
    return this.http.post<CartItem>(this.apiUrl, cartItem);
  }

  updateCartItem(cartItem: CartItem): Observable<CartItem> {
    return this.http.put<CartItem>(`${this.apiUrl}/${cartItem.id}`, cartItem);
  }

  removeCartItem(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }
}
