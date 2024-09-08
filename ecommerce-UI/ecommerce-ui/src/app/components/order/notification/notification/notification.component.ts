import { Component } from '@angular/core';
import { WebSocketService } from '../../../../services/websocket/web-socket.service';

@Component({
  selector: 'app-notification',
  templateUrl: './notification.component.html',
  styleUrl: './notification.component.scss'
})
export class NotificationComponent {

  notifications: string[] = [];

  constructor(private webSocketService: WebSocketService) {}

  ngOnInit(): void {
    this.notifications = this.webSocketService.getStoredNotifications();
    this.webSocketService.getOrderUpdates().subscribe((messages: string[]) => {
      this.notifications = messages;
      console.log("hello notification recieved:", this.notifications);
      this.webSocketService.resetUnreadCount();
    });
  }

}
