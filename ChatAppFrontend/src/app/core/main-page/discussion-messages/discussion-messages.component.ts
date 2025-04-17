import {Component, ElementRef, inject, ViewChild} from '@angular/core';
import {FormsModule} from '@angular/forms';
import {CommonModule} from "@angular/common";
import {ActivatedRoute} from "@angular/router";
import {DiscussionService} from "../../services/discussion.service";
import {Message} from "../../interfaces/message";
import {Subscription} from "rxjs";
import {WebsocketService} from "../../services/websocket.service";
import {AuthService} from "../../services/auth.service";


@Component({
  selector: 'app-discussion-messages',
  standalone: true,
  imports: [
    FormsModule,
    CommonModule
  ],
  template: `
    <div class="chat-container">
      <div class="discussion-title">
        <p>Discussion {{ this.discussionId }}</p>
      </div>
      <div #messagesDiv class="messages">
        <div *ngFor="let message of messages"
             [class.sent]="message?.senderId === this.authService.userId"
             [class.received]="!(message?.senderId === this.authService.userId)"
             class="message">
          {{ message?.content }}
        </div>
      </div>
      <div class="input-area">
        <input type="text"
               [(ngModel)]="newMessage"
               (keyup.enter)="sendMessage()"
               placeholder="Message..."/>
        <button (click)="sendMessage()">Send</button>
      </div>
    </div>
  `,
  styleUrl: './discussion-messages.component.css'
})
export class DiscussionMessagesComponent {

  websocketService: WebsocketService = inject(WebsocketService);
  discussionService: DiscussionService = inject(DiscussionService);
  authService: AuthService = inject(AuthService);
  route: ActivatedRoute = inject(ActivatedRoute);

  newMessage: string = '';
  messages: Message[] | undefined = [];
  currentSub !: Subscription;
  discussionId: number = 0;
  @ViewChild('messagesDiv') messagesDiv !: ElementRef<HTMLDivElement>;

  constructor() {
    console.log(this.authService.userId)
    this.currentSub = this.route.params.subscribe(params => {
      this.discussionId = Number(params["id"]);
      this.discussionService.getMessagesByDiscussionId(this.discussionId)
        .subscribe(messages => {
          console.log(messages);
          this.messages = messages;
        });
    });
  }


  ngOnDestroy() {
    this.currentSub.unsubscribe();
  }


  ngAfterViewChecked(): void {
    this.scrollToBottom();
  }

  sendMessage() {
    if (this.newMessage.trim()) {
      const message: Message = {
        content: this.newMessage,
        discussionId: this.discussionId,
      } as Message;
      this.websocketService.sendMessage(message);
      this.newMessage = '';
    }
  }

  private scrollToBottom() {
    this.messagesDiv.nativeElement.scrollTop = this.messagesDiv.nativeElement.scrollHeight;
  }

}
