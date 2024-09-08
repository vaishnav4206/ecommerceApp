import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AdminGuard } from './services/guards/admin.guard';
import { UserGuard } from './services/guards/user.guard';
import { AdminComponent } from './components/admin/admin.component';
import { UserComponent } from './components/user/user.component';
import { UnauthorizedComponent } from './components/unauthorized/unauthorized.component';
import { ProductComponent } from './components/product/product.component';
import { CartComponent } from './components/cart/cart/cart.component';
import { NotificationComponent } from './components/order/notification/notification/notification.component';
import { OrderHomeComponent } from './components/order/order-home/order-home/order-home.component';

const routes: Routes = [
  { path: 'admin', component: AdminComponent, canActivate: [AdminGuard] },
  { path: 'products', component: ProductComponent, canActivate: [UserGuard] },
  { path: 'user', component: UserComponent, canActivate: [UserGuard] },
  { path: 'unauthorized', component: UnauthorizedComponent },
  { path: 'cart', component: CartComponent },
  { path: 'orders', component: OrderHomeComponent },
  { path: 'notification', component: NotificationComponent },
  { path: '', redirectTo: 'products', pathMatch: 'full' }
];


@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
