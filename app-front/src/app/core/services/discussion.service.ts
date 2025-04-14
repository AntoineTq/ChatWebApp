import {Injectable} from '@angular/core';
import {Message} from "../interfaces/message";
import {HttpClient} from "@angular/common/http";
import {environment} from "../../../environments/environment";
import {AuthService} from "./auth.service";
import {Discussion} from "../interfaces/discussion";


@Injectable({
  providedIn: 'root'
})
export class DiscussionService {
  private apiUrl = environment.apiUrl;


  constructor(private http: HttpClient, private auth: AuthService) {
  }



  getMessagesByDiscussionId(id: number): Message[] {
    return [];
  }


}
