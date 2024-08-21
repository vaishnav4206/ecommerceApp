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
      
      console.log("my profile",this._profile)
      if(this._profile.sub !== null || this._profile.sub !== ''){
        sessionStorage.setItem('userId', this._profile.sub);
      }
    }
  }

  login() {
    return this.keyCloak?.login();
  }

  logout() {
    return this.keyCloak?.logout({redirectUri: 'http://localhost:4200'});
  }

  // hasRole(role: string): boolean {
  //   console.log("I have theses roles : ", this.keyCloak?.tokenParsed?.realm_access?.roles)
  //   return this.keyCloak?.tokenParsed?.realm_access?.roles?.includes(role) ?? false;
  // }

  hasRole(role: string): boolean {
    const clientId = this.keyCloak?.clientId;
  
    if (!clientId) {
      console.error('Client ID is undefined.');
      return false;
    }
  
    const resourceAccess = this.keyCloak?.tokenParsed?.resource_access;
  
    // Check if the role exists in the roles array for the given client ID
    const clientRoles = resourceAccess?.[clientId]?.roles || [];
    console.log("my roles in resource: ", clientRoles)
    return clientRoles.includes(role);
  }
  

}
