import { Component, Input } from '@angular/core';
import { Discussion } from "../../interfaces/discussion";
import { RouterModule} from "@angular/router";

@Component({
  selector: 'app-discussions-list-elem',
  standalone: true,
  imports: [ RouterModule ],
  template: `
    <a class="listing" [routerLink]="['/discussions', discussion.id]">
        <img class="listing-photo" src="../../../assets/account_circle.svg" alt="default discussion icon">
        <h2 class="listing-header">{{ discussion.name }}</h2>
    </a>
  `,
  styleUrl: './discussions-list-elem.component.css'
})
export class DiscussionsListElemComponent {
  @Input() discussion!:Discussion;

}
