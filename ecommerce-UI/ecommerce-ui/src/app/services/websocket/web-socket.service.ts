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
  private orderUpdatesSubject: Subject<string> = new Subject<string>();

  constructor(private keycloakService: KeycloakService) {
    const socket = new SockJS('http://localhost:8082/ws-notifications');
    this.stompClient = new Client({
      webSocketFactory: () => socket,
      debug: (str) => console.log("debug:",str),
      onConnect: () => {
        this.stompClient.subscribe('/topic/order-status-updates', (message) => {
          console.log("message from stomp client: ", message)
          this.orderUpdatesSubject.next(message.body);
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

  getOrderUpdates(): Observable<string> {
    return this.orderUpdatesSubject.asObservable();
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
