import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';
import { KeycloakService } from '../keycloak/keycloak.service';

export const authGuard: CanActivateFn = (route, state) => {
  const keycloakService = inject(KeycloakService);
  const router = inject(Router);
  if(keycloakService.keyCloak.isTokenExpired()){
    router.navigate(['login']);
    return false;
  }
  return true;
};
