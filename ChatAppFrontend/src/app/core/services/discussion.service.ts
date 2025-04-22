import {inject, Injectable} from '@angular/core';
import {Message} from "../interfaces/message";
import {HttpClient} from "@angular/common/http";
import {environment} from "../../../environments/environment";
import {AuthService} from "./auth.service";
import {Discussion} from "../interfaces/discussion";
import {BehaviorSubject, Observable, Subject} from "rxjs";


@Injectable({
  providedIn: 'root'
})
export class DiscussionService {
  discussionList: Discussion[] = [];
  private activeDiscussionMessagesSubject : BehaviorSubject<Message[]>;
  public activeDiscussionMessages;
  private apiUrl = environment.apiUrl;

  constructor(private http: HttpClient, private auth: AuthService) {
    this.activeDiscussionMessagesSubject = new BehaviorSubject<Message[]>([]);
    this.activeDiscussionMessages = this.activeDiscussionMessagesSubject.asObservable();
  }

  getDiscussions() {
    const headers = this.auth.getMyJwtAuthHeader();
    this.http.get<Discussion[]>(this.apiUrl + '/api/v1/discussions', {headers})
      .subscribe(response => {
        console.log("received discussion list : " + response);
        this.discussionList = response;
      })
  }


  getMessagesByDiscussionId(id: number) {
    console.log("query messages of discussion " + id);
    const headers = this.auth.getMyJwtAuthHeader();
    this.http.get<Message[]>(this.apiUrl + `/api/v1/discussions/${id}`, {headers})
      .subscribe( res =>{
        this.activeDiscussionMessagesSubject.next(res);
      });
  }

  addNewMessage(message:Message){
    const messages = this.activeDiscussionMessagesSubject.value;
    const newMessages  = [...messages,message];
    this.activeDiscussionMessagesSubject.next(newMessages);
  }


}
