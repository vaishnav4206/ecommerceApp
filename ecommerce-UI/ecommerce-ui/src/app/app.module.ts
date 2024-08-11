import { APP_INITIALIZER, NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { KeycloakService } from './services/keycloak/keycloak.service';
import { AdminComponent } from './components/admin/admin.component';
import { UserComponent } from './components/user/user.component';
import { UnauthorizedComponent } from './components/unauthorized/unauthorized.component';
import { ProductComponent } from './components/product/product.component';

export function keycloakFactory(keycloakService: KeycloakService) {
  return () => keycloakService.init();
}

@NgModule({
  declarations: [
    AppComponent,
    AdminComponent,
    UserComponent,
    UnauthorizedComponent,
    ProductComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule
  ],
  providers: [
    {
      provide: APP_INITIALIZER,
      deps: [KeycloakService],
      useFactory: keycloakFactory,
      multi: true
    }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
