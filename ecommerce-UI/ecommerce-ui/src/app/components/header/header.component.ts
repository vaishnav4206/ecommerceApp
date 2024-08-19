import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { KeycloakService } from '../../services/keycloak/keycloak.service';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrl: './header.component.scss'
})
export class HeaderComponent {

  constructor(private keycloakService: KeycloakService, private router: Router) { }

  logout(): void {
    this.keycloakService.logout().then(() => {
      this.router.navigate(['/']);
    });
  }

}
