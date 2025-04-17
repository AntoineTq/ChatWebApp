import {inject, Injectable} from '@angular/core';
import SockJS from 'sockjs-client';
import * as Stomp from 'stompjs';
import {Message} from "../interfaces/message";
import {AuthService} from "./auth.service";
import {environment} from "../../../environments/environment";

@Injectable({
  providedIn: 'root'
})
export class WebsocketService {
  private stompClient: any;
  private socket: WebSocket | null = null;
  private authService: AuthService = inject(AuthService);
  private apiUrl = environment.apiUrl;

  constructor() {
    this.authService.hasMyJwt.subscribe( hasToken => {
      if (hasToken) {
        this.initWebSocket();
      } else {
        if (this.stompClient != undefined) {
          this.stompClient.disconnect();
        }
      }
    });
  }

  initWebSocket() {
    this.socket = new SockJS(`${this.apiUrl}/ws`);
    this.stompClient = Stomp.over(this.socket);
    // this.stompClient.debug = null; TODO add
    const token = this.authService.getMyJwt();
    this.stompClient.connect({
      Authorization: `Bearer ${token}`
    }, () => {
      this.stompClient.subscribe('/topic/messages', (msg: any) => {

      });
    });
  }


  sendMessage(msg: Message): void {
    console.log("New Message Sent : " + msg.content + " "+ msg.discussionId);
    const headers = this.authService.getMyJwtAuthHeader();
    this.stompClient.send('/app/message', {}, JSON.stringify(msg));
  }

}
