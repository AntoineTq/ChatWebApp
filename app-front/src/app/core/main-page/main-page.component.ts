import {Component, inject} from '@angular/core';
import {DiscussionsListComponent} from "./discussions-list/discussions-list.component";
import {UserStatusComponent} from "./user-status/user-status.component";
import {DiscussionMessagesComponent} from "./discussion-messages/discussion-messages.component";
import {WebsocketService} from "../services/websocket.service";
import {ActivatedRoute} from "@angular/router";
import {CommonModule} from "@angular/common";
import {AuthService} from "../services/auth.service";

@Component({
  selector: 'app-main-page',
  standalone: true,
  imports: [
    DiscussionsListComponent,
    UserStatusComponent,
    DiscussionMessagesComponent,
    CommonModule
  ],
  template: `
    <app-user-status></app-user-status>
    <div class="page-content">
      <div class="discussions-list">
        <app-discussions-list></app-discussions-list>
      </div>
      <div class="discussion-content">
        <div *ngIf="isOpenedDiscussion">
          <app-discussion-messages></app-discussion-messages>
        </div>

        <p *ngIf="!isOpenedDiscussion"> Opened discussions will be displayed here</p>
      </div>
    </div>


  `,
  styleUrl: './main-page.component.css'
})
export class MainPageComponent {

  route: ActivatedRoute = inject(ActivatedRoute);
  isOpenedDiscussion = false;

  constructor(private websocketService : WebsocketService) {
    this.route.paramMap.subscribe( params => {
      this.isOpenedDiscussion = params.has("id");
    });
  }

}
