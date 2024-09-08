import { Component } from '@angular/core';
import { MatDialogRef } from '@angular/material/dialog';
import { Router } from '@angular/router';

@Component({
  selector: 'app-order-confirmation-dialog',
  templateUrl: './order-confirmation-dialog.component.html',
  styleUrl: './order-confirmation-dialog.component.scss'
})
export class OrderConfirmationDialogComponent {

  constructor(
    public dialogRef: MatDialogRef<OrderConfirmationDialogComponent>,
    private router: Router
  ) {}

  viewMyOrders(): void {
    this.dialogRef.close();
    this.router.navigate(['/orders']);
  }

  continueShopping(): void {
    this.dialogRef.close();
    this.router.navigate(['/products']);
  }

}
