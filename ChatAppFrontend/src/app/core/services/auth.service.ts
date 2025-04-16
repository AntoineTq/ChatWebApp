import {inject, Injectable, signal} from '@angular/core';
import {AuthConfig, OAuthService} from 'angular-oauth2-oidc';
import {Router} from "@angular/router";
import {BehaviorSubject} from "rxjs";


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

  private oAuthService = inject(OAuthService);
  private router = inject(Router);
  private currentUserSubject: BehaviorSubject<any | null>;
  public currentUser;

  constructor() {
    this.currentUserSubject = new BehaviorSubject<any | null>(null);
    this.currentUser = this.currentUserSubject.asObservable();
    this.initConfiguration();
  }

  private initConfiguration() {
    this.oAuthService.configure(authConfig);
    return this.oAuthService.loadDiscoveryDocumentAndTryLogin().then(() => {

      if (this.oAuthService.hasValidIdToken()) {
        console.log(this.oAuthService.getIdToken());
        this.currentUserSubject.next(this.oAuthService.getIdentityClaims());
      }
    });
  }


  login() {
    this.oAuthService.initLoginFlow();
  }

  logout() {
    this.oAuthService.revokeTokenAndLogout()
    this.oAuthService.logOut();
    this.currentUserSubject.next(null);
  }

  getJWT() {
    return this.oAuthService.getIdToken();
  }

  getAccessToken(){
    return this.oAuthService.getAccessToken();
  }

  getUserProfile(){
    return this.currentUser;
  }

  getUserName() {
    const claims = this.oAuthService.getIdentityClaims();
    return claims ? claims['name'] : null;
  }

  isAuthenticated() {
    return this.oAuthService.hasValidAccessToken();
  }
}
