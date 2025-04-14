import {Component} from '@angular/core';
import {Router} from "@angular/router";
import {AuthService} from "../../services/auth.service";
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {MyJwt} from "../../interfaces/token";


@Component({
  selector: 'app-user-status',
  standalone: true,
  imports: [],
  template: `
    <section class="user-status">
      <p>
        {{ user }}
      </p>
      <button (click)="disconnect()">disconnect</button>
      <button (click)="authenticateUser()">authentic</button>
      <button (click)="getProtectedResource()">test get resource</button>
    </section>

  `,
  styleUrl: './user-status.component.css'
})
export class UserStatusComponent {

  user: string = '';

  constructor(private router: Router, private authService: AuthService, private http: HttpClient) {
  }

  ngOnInit() {
    this.user = this.authService.getUserName();
  }

  disconnect() {
    this.authService.logout();
    this.router.navigate(['/']);
  }

  authenticateUser() {
    const token = this.authService.getJWT();
    return this.http.post<MyJwt>('http://localhost:8080/auth/login',
      {token}).subscribe(response => {
      console.log(response);
      sessionStorage.setItem("myJWT", response.token);
    });
  }

  getProtectedResource() {
    const token = sessionStorage.getItem("myJWT");
    const headers = new HttpHeaders({
      Authorization: `Bearer ${token}`
    });
    this.http.get('http://localhost:8080/protected', {headers})
      .subscribe(response => {
        console.log(response);
      })
  }
}

