import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class PostService {

  constructor(public httpClient: HttpClient) {
    this.httpClient = httpClient;
  }
  private baseUrl: string = 'http://localhost:8080/savePost';
  savePost(formData: FormData):Observable<any>{
    let headers = new HttpHeaders();
    headers.append('Content-Type', 'multipart/form-data');
    return this.httpClient.post<any>(this.baseUrl, formData, {
      headers
    });
  }
}
