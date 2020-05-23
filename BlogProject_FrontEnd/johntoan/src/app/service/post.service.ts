import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Observable} from "rxjs";
import {Post} from "../examples/model/Post";
import {MediaService} from "./media.service";

@Injectable({
  providedIn: 'root'
})
export class PostService {

  postList: Post[] = [];
  constructor(public httpClient: HttpClient) {
    this.getAllPost();
  }
  private baseUrl: string = 'http://localhost:8080/';

  savePost(formData: FormData):Observable<any>{
    let headers = new HttpHeaders();
    headers.append('Content-Type', 'multipart/form-data');
    return this.httpClient.post<any>(this.baseUrl + 'savePost', formData, {
      headers
    });
  }

  fetchAllPostFromAPI(){
    return this.httpClient.get<Post[]>(this.baseUrl +'getAllPosts');
  }

  getAllPost(){
    this.fetchAllPostFromAPI().subscribe((resJson) => {
      this.postList = resJson;
    });
  }

  getOnePost(postId:number){
    return this.postList.find(e => e.id === postId);
  }
}
