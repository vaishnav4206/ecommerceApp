import { Injectable } from '@angular/core';
import { CanActivate, Router } from '@angular/router';
import { KeycloakService } from '../keycloak/keycloak.service';

@Injectable({
  providedIn: 'root'
})
export class UserGuard implements CanActivate {

  constructor(private keycloakService: KeycloakService, private router: Router) {}

  canActivate(): boolean {
    if (this.keycloakService.hasRole('client-user')) {
      return true;
    } else {
      this.router.navigate(['unauthorized']);
      return false;
    }
  }
}
