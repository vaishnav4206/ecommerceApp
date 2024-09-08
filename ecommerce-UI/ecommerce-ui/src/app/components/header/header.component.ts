import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { KeycloakService } from '../../services/keycloak/keycloak.service';
import { WebSocketService } from '../../services/websocket/web-socket.service';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrl: './header.component.scss'
})
export class HeaderComponent {

  unreadCount: number = 0;

  constructor(private keycloakService: KeycloakService, private router: Router, private webSocketService: WebSocketService) { }

  ngOnInit(): void {
    this.webSocketService.getOrderUpdates().subscribe((messages: string[]) => {
      this.unreadCount = messages.length; 
    });
  }

  logout(): void {
    this.keycloakService.logout().then(() => {
      this.router.navigate(['/']);
    });
  }

}
