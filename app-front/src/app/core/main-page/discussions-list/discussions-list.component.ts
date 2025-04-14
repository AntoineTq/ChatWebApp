import {Component, inject} from '@angular/core';
import { CommonModule} from "@angular/common";
import { Discussion} from "../../interfaces/discussion";
import {DiscussionsListElemComponent} from "../discussion-list-element/discussions-list-elem.component";
import {DiscussionService} from "../../services/discussion.service";
import {AuthService} from "../../services/auth.service";
import {filter, switchMap} from "rxjs";

@Component({
  selector: 'app-discussions-list',
  standalone: true,
  imports: [DiscussionsListElemComponent, CommonModule],
  template: `
    <div class="discussion-list-title">
      <p>Discussions</p>
    </div>

    <section>
        <app-discussions-list-elem *ngFor="let discussion of discussionList" [discussion]="discussion"></app-discussions-list-elem>
    </section>

  `,
  styleUrl: './discussions-list.component.css'
})
export class DiscussionsListComponent {
  discussionList: Discussion[] = [];


  constructor() {


  }
}
