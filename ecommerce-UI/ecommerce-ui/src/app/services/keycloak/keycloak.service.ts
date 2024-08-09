import { Injectable } from '@angular/core';
import Keycloak from 'keycloak-js';
import { UserProfile } from '../../model/user-profile';

@Injectable({
  providedIn: 'root'
})
export class KeycloakService {

  private _keycloak: Keycloak | undefined;
  private _profile: UserProfile | undefined;

  get keyCloak() {
    if(!this._keycloak){
      this._keycloak = new Keycloak({
        url: 'http://localhost:8080',
        realm: 'ecommerce',
        clientId: 'ecommerce-rest-api'
      })
    }
    return this._keycloak;
  }

  get profile(): UserProfile | undefined {
    return this._profile;
  }

  constructor() { }

  async init() {
    console.log("authenticating user ...")
    const authenticated = await this.keyCloak?.init({
      onLoad: 'login-required'
    });

    if(authenticated){
      this._profile = (await this.keyCloak?.loadUserProfile()) as UserProfile;
      this._profile.token = this.keyCloak?.token;
    }
  }

  login() {
    return this.keyCloak?.login();
  }

  logout() {
    return this.keyCloak?.logout({redirectUri: 'http://localhost:4200'});
  }

}
