import { Component, OnInit } from '@angular/core';
import { OrderService } from '../../../../services/order/order.service';
import { Order } from '../../../../model/order';

@Component({
  selector: 'app-order-home',
  templateUrl: './order-home.component.html',
  styleUrl: './order-home.component.scss'
})
export class OrderHomeComponent implements OnInit {

  orders: Order[] = [];
  userId: string | null = sessionStorage.getItem('userId');

  constructor(private orderService: OrderService) {}

  ngOnInit(): void {
    this.loadOrders();
  }

  loadOrders(): void {
    if (this.userId !== null) {
      this.orderService.getOrders(this.userId).subscribe((data: Order[]) => {
        this.orders = data;
        this.sortOrdersByDate();
      });
    }
  }

  sortOrdersByDate(): void {
    this.orders.sort((a, b) => {
      return new Date(b.createdAt).getTime() - new Date(a.createdAt).getTime();
    });
  }

}
