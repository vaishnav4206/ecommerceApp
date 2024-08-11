import { Component } from '@angular/core';
import { KeycloakService } from '../../services/keycloak/keycloak.service';

@Component({
  selector: 'app-unauthorized',
  templateUrl: './unauthorized.component.html',
  styleUrl: './unauthorized.component.scss'
})
export class UnauthorizedComponent {

  constructor(private keycloakService: KeycloakService) {}

  logout() {
    this.keycloakService.logout();
  }

}
