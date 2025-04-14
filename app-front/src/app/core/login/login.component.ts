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

  constructor(private router: Router, private http: HttpClient) {
    this.authService.currentUser.subscribe(user => {
      if (user) {
        console.log("User connected");
        router.navigate(['/discussions'])
        this.authenticateUser();
      }
    })

  }

  login() {
    this.authService.login();
  }


  authenticateUser() {
    const token = this.authService.getJWT();
    return this.http.post<MyJwt>('http://localhost:8080/auth/login',
      {token}).subscribe(response => {
      console.log(response);
      sessionStorage.setItem("myJWT", response.token);
    });
  }

}
