import {inject, Injectable} from '@angular/core';
import {Message} from "../interfaces/message";
import {HttpClient} from "@angular/common/http";
import {environment} from "../../../environments/environment";
import {AuthService} from "./auth.service";
import {Discussion} from "../interfaces/discussion";
import {Observable} from "rxjs";


@Injectable({
  providedIn: 'root'
})
export class DiscussionService {
  discussionList: Discussion[] = [];
  private apiUrl = environment.apiUrl;
  private http = inject(HttpClient);
  private auth = inject(AuthService);


  getDiscussions() {
    const headers = this.auth.getMyJwtAuthHeader();
    this.http.get<Discussion[]>(this.apiUrl + '/api/v1/discussions', {headers})
      .subscribe(response => {
        console.log("received discussion list : " + response);
        this.discussionList = response;
      })
  }


  getMessagesByDiscussionId(id: number): Observable<Message[]> {
    console.log("query messages of discussion " + id);
    const headers = this.auth.getMyJwtAuthHeader();
    return this.http.get<Message[]>(this.apiUrl + `/api/v1/discussions/${id}`, {headers});
  }


}
