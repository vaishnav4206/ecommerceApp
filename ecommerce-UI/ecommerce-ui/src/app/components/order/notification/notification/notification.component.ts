import { Component } from '@angular/core';
import { WebSocketService } from '../../../../services/websocket/web-socket.service';

@Component({
  selector: 'app-notification',
  templateUrl: './notification.component.html',
  styleUrl: './notification.component.scss'
})
export class NotificationComponent {

  notification: string | null = null;

  constructor(private webSocketService: WebSocketService) {}

  ngOnInit(): void {
    this.webSocketService.getOrderUpdates().subscribe((message: string) => {
      this.notification = `Order Status Update: ${message}`;
      console.log("hello notification recieved:", this.notification)
    });
  }

}
