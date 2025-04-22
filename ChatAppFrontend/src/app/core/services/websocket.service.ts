import {inject, Injectable} from '@angular/core';
import SockJS from 'sockjs-client';
import * as Stomp from 'stompjs';
import {Message} from "../interfaces/message";
import {AuthService} from "./auth.service";
import {environment} from "../../../environments/environment";
import {DiscussionService} from "./discussion.service";
import {Discussion} from "../interfaces/discussion";

@Injectable({
  providedIn: 'root'
})
export class WebsocketService {
  private stompClient: any;
  private socket: WebSocket | null = null;
  private apiUrl = environment.apiUrl;

  constructor(private discussionService: DiscussionService, private authService:AuthService) {
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
      this.stompClient.subscribe(`/user/${this.authService.userId}/queue/messages`, (msg: any) => {

        const message : Message = JSON.parse(msg.body);
        console.log("received on socket :"+message);
        this.handleMessage(message);

      });
    });
  }


  sendMessage(msg: Message): void {
    console.log("New Message Sent : " + msg.content + " "+ msg.discussionId);
    const headers = this.authService.getMyJwtAuthHeader();
    this.stompClient.send('/app/message', {}, JSON.stringify(msg));
  }

  private handleMessage(message: Message) {
    if (!message) return;
    this.discussionService.addNewMessage(message);

    //TODO change this to handle active discussion + others like :
    /*
    if(this.activeDisc == message.discussionId){
      this.discussionService.addNewMessage(message); // add msg to display
    }else{
      set the discussion at the top of the list to notify new msg
    }
     */

  }
}
