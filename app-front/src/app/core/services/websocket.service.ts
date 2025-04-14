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
  // private stompClient: any;
  // private socket: WebSocket | undefined;
  // private authService: AuthService = inject(AuthService);
  // private apiUrl = environment.apiUrl;


  constructor() {
    // this.authService.currentUser.subscribe(user => {
    //   if (!user) {
    //     this.stompClient.disconnect();
    //   } else {
    //     this.initWebSocket();
    //   }
    // })
  }

  initWebSocket() {
    // this.socket = new SockJS(`${this.apiUrl}/ws`);
    // this.stompClient = Stomp.over(this.socket);
    // // this.stompClient.debug = null; TODO add
    // this.stompClient.connect({/* TODO Add authorisation header here when adding security*/}, () => {
    //   this.stompClient.subscribe('/topic/messages', (msg: any) => {
    //   });
    // });
  }


  sendMessage(msg: Message): void {
    // console.log("New Message Sent : " + msg);
    // this.stompClient.send('/app/message', {}, JSON.stringify(msg));
  }

}
