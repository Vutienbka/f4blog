import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Observable} from "rxjs";
import {Post} from "../examples/model/Post";


@Injectable({
  providedIn: 'root'
})
export class PostService {

  postList: Post[] = [];
  // categoryList:CategoryEntity[]=[{name:"finance"},{name:"IT"},{name:"banking"},{name:"education"},{name:"sport"}];
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

  updateAfterEdit(formData: FormData, postId:number):Observable<any> {
    let headers = new HttpHeaders();
    headers.append('Content-Type', 'multipart/form-data');
    return this.httpClient.post<any>(this.baseUrl + 'updatePost/'+ postId, formData, {
      headers
    });
  }
  timeConverter(UNIX_timestamp){
    var a = new Date(UNIX_timestamp);
    var months = ['Jan','Feb','Mar','Apr','May','Jun','Jul','Aug','Sep','Oct','Nov','Dec'];
    var year = a.getFullYear();
    var month = months[a.getMonth()];
    var date = a.getDate();
    var hour = a.getHours();
    var min = a.getMinutes();
    var sec = a.getSeconds();
    var time = date + ' ' + month + ' ' + year + ' ' + hour + ':' + min + ':' + sec ;
    return time;
  }

  deletePost(postId: number){
    return this.httpClient.delete(this.baseUrl + 'deletePost/' + postId);
  }
}
