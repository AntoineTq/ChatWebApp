import {inject, Injectable} from '@angular/core';
import {AuthConfig, OAuthService} from 'angular-oauth2-oidc';
import {Router} from "@angular/router";
import {BehaviorSubject} from "rxjs";
import {HttpClient, HttpHeaders} from "@angular/common/http";


const authConfig: AuthConfig = {
  issuer: 'https://accounts.google.com',
  redirectUri: 'http://localhost:4200',
  clientId: '169246276138-atp6qataud7klsblcis79oh0hhsvrrvn.apps.googleusercontent.com',
  scope: 'openid profile email',
  strictDiscoveryDocumentValidation: false,
  showDebugInformation: true,
};


@Injectable({
  providedIn: 'root'
})
export class AuthService {

  public currentUser;
  public hasMyJwt;
  private oAuthService = inject(OAuthService);
  private http: HttpClient = inject(HttpClient);
  private router: Router = inject(Router);
  private currentUserSubject: BehaviorSubject<any | null>;
  private hasMyJwtSubject: BehaviorSubject<boolean | null>;

  constructor() {
    this.currentUserSubject = new BehaviorSubject<any | null>(null);
    this.currentUser = this.currentUserSubject.asObservable();

    this.hasMyJwtSubject = new BehaviorSubject<boolean | null>(false);
    this.hasMyJwt = this.hasMyJwtSubject.asObservable();

    this.initConfiguration();
  }

  private _userId: number = -1;

  get userId(): number {
    return this._userId;
  }

  login() {
    this.oAuthService.initLoginFlow();
  }

  logout() {
    this.oAuthService.revokeTokenAndLogout()
    this.oAuthService.logOut();
    sessionStorage.removeItem("myJWT");
    this.hasMyJwtSubject.next(false);
    this.currentUserSubject.next(null);
  }

  getIdToken() {
    return this.oAuthService.getIdToken();
  }

  getUserName() {
    const claims = this.oAuthService.getIdentityClaims();
    return claims ? claims['name'] : null;
  }

  getUserEmail() {
    const claims = this.oAuthService.getIdentityClaims();
    return claims ? claims['email'] : null;
  }

  isAuthenticated() {
    return this.oAuthService.hasValidAccessToken() && this.hasMyJwt;
  }

  getMyJwt() {
    return sessionStorage.getItem("myJWT");
  }


  getMyJwtAuthHeader() {
    const token = this.getMyJwt();
    return new HttpHeaders({
      Authorization: `Bearer ${token}`
    });
  }

  private authenticateUser() {
    const token = this.getIdToken();
    return this.http.post('http://localhost:8080/auth/login', {token})
      .subscribe((response: any) => {
        console.log("user connected and received myJWT");
        sessionStorage.setItem("myJWT", response.token);
        this._userId = response.userId;
        this.hasMyJwtSubject.next(true);
        this.router.navigate(['/discussions']);
      });
  }

  private initConfiguration() {
    this.oAuthService.configure(authConfig);
    return this.oAuthService.loadDiscoveryDocumentAndTryLogin().then(() => {
      if (this.oAuthService.hasValidIdToken()) {
        this.currentUserSubject.next(this.oAuthService.getIdentityClaims());
        this.authenticateUser();
      }
    });
  }


}
