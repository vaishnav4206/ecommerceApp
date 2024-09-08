import { Injectable } from '@angular/core';
import { Client } from '@stomp/stompjs';
import SockJS from 'sockjs-client';
import { Observable, Subject } from 'rxjs';
import { KeycloakService } from '../keycloak/keycloak.service';

@Injectable({
  providedIn: 'root'
})
export class WebSocketService {


  private stompClient: Client;
  private notifications: string[] = [];
  private notificationSubject: Subject<string[]> = new Subject<string[]>();
  private unreadCount: number = 0;
  private unreadCountSubject: Subject<number> = new Subject<number>();
  //private orderUpdatesSubject: Subject<string> = new Subject<string>();

  constructor(private keycloakService: KeycloakService) {
    const socket = new SockJS('http://localhost:8082/ws-notifications');
    this.stompClient = new Client({
      webSocketFactory: () => socket,
      debug: (str) => console.log("debug:",str),
      onConnect: () => {
        this.stompClient.subscribe('/topic/order-status-updates', (message) => {
          console.log("message from stomp client: ", message)
          this.notifications.push(message.body);
          this.notificationSubject.next([...this.notifications]);
          this.incrementUnreadCount();
          //this.orderUpdatesSubject.next(message.body);
        });
      },
      onStompError: (frame) => {
        console.error('Broker reported error: ' + frame.headers['message']);
        console.error('Additional details: ' + frame.body);
      },
      connectHeaders: {
        // Add authentication token to the connection headers
        Authorization: `Bearer ${this.keycloakService.getToken()}`
      }
    });
    this.stompClient.activate();
  }

  getOrderUpdates(): Observable<string[]> {
    return this.notificationSubject.asObservable(); 
    //return this.orderUpdatesSubject.asObservable();
  }

  getStoredNotifications(): string[] {
    return this.notifications;
  }

  getUnreadCount(): Observable<number> {
    return this.unreadCountSubject.asObservable();  // Observable for the unread count
  }

  private incrementUnreadCount(): void {
    this.unreadCount++;
    this.unreadCountSubject.next(this.unreadCount);  // Emit new unread count
  }

  resetUnreadCount(): void {
    this.unreadCount = 0;
    this.unreadCountSubject.next(this.unreadCount);  // Reset unread count and emit the update
  }
}


// import { Injectable } from '@angular/core';
// import { Client, IMessage } from '@stomp/stompjs';
// import SockJS from 'sockjs-client';
// // import * as SockJS from 'sockjs-client';

// @Injectable({
//   providedIn: 'root',
// })
// export class WebSocketService {
//   private client: Client;

//   constructor() {
//     this.client = new Client({
//       webSocketFactory: () => new SockJS('http://localhost:8082/ws-notifications'),
//       reconnectDelay: 5000,
//     });

//     this.client.onConnect = () => {
//       this.client.subscribe('/topic/order-status', (message: IMessage) => {
//         this.onMessageReceived(message);
//       });
//     };

//     this.client.activate();
//   }

//   private onMessageReceived(message: IMessage) {
//     console.log('Message received from server: ', message.body);
//     // You can add additional logic here to handle the message
//   }

//   public sendMessage(destination: string, body: string) {
//     this.client.publish({ destination, body });
//   }
// }
