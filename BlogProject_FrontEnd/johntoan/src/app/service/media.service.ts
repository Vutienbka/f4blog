import { Injectable } from '@angular/core';
import {Post} from "../examples/model/Post";
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Observable} from "rxjs";
import {Media} from "../examples/model/Media";

@Injectable({
  providedIn: 'root'
})
export class MediaService {

  mediaList: Media[] = [];

  constructor(public httpClient: HttpClient) {
    this.getAllMedias();
  }
  private baseUrl: string = 'http://localhost:8080/';

  fetchAllMediaFromAPI(){
    return this.httpClient.get<Media[]>(this.baseUrl +'getAllMedias');
  }

  getAllMedias(){
    this.fetchAllMediaFromAPI().subscribe((resJson) => {
      this.mediaList = resJson;
    });
  }

  getOneMedia(postId:number){
    return this.mediaList.find(e => e.id === postId);
  }
}
