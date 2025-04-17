import {Component, inject} from '@angular/core';
import {FormsModule} from "@angular/forms";
import {AuthService} from "../services/auth.service";
import {HttpClient} from "@angular/common/http";
import {Router} from "@angular/router";
import {MyJwt} from "../interfaces/token";

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [FormsModule],
  template: `
    <div class="login">
      <p>Login</p>
      <button (click)="authService.login()">Login with Google</button>
    </div>
  `,
  styleUrl: './login.component.css'
})
export class LoginComponent {
  authService = inject(AuthService);

  login() {
    this.authService.login();
  }




}
