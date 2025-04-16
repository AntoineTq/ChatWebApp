import {Routes} from '@angular/router';
import {LoginComponent} from "./core/login/login.component";
import {MainPageComponent} from "./core/main-page/main-page.component";
import {authGuard} from "./core/guard/auth.guard";

export const routes: Routes = [
  {path: '', component: LoginComponent},
  {path: 'login', redirectTo: '', pathMatch: "full"},
  {path: 'discussions', component: MainPageComponent, canActivate: [authGuard]},
  {path: 'discussions/:id', component: MainPageComponent, canActivate: [authGuard]}
];
