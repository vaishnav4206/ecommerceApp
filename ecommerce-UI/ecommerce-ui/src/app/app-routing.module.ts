import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AdminGuard } from './services/guards/admin.guard';
import { UserGuard } from './services/guards/user.guard';
import { AdminComponent } from './components/admin/admin.component';
import { UserComponent } from './components/user/user.component';
import { UnauthorizedComponent } from './components/unauthorized/unauthorized.component';

const routes: Routes = [
  { path: 'admin', component: AdminComponent, canActivate: [AdminGuard] },
  { path: 'user', component: UserComponent, canActivate: [UserGuard] },
  { path: 'unauthorized', component: UnauthorizedComponent },
  { path: '', redirectTo: 'user', pathMatch: 'full' }
];


@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
