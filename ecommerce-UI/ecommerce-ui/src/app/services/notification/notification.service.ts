// import { Injectable } from '@angular/core';
// import { StompService } from '@stomp/stompjs';
// import { Observable } from 'rxjs';
// import { Message } from '@stomp/stompjs';

// @Injectable({
//   providedIn: 'root'
// })
// export class NotificationService {

//   private topicUrl = '/topic/order-status-updates';

//   constructor(private stompService: StompService) {}

//   public getOrderStatusUpdates(): Observable<Message> {
//     return this.stompService.subscribe(this.topicUrl);
//   }
// }
