import {Component} from '@angular/core';
import {Router} from "@angular/router";
import {AuthService} from "../../services/auth.service";
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {MyJwt} from "../../interfaces/token";
import {BehaviorSubject} from "rxjs";
import {TitleCasePipe} from "@angular/common";


@Component({
  selector: 'app-user-status',
  standalone: true,
  imports: [
    TitleCasePipe
  ],
  template: `
    <section class="user-status">
      <p>
        {{ user | titlecase}}
      </p>
      <button (click)="disconnect()">disconnect</button>
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

}

