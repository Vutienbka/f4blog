import { Injectable } from '@angular/core';
import {BehaviorSubject, Observable} from "rxjs";
import {UserJwt} from "../examples/model/UserJwt";
import {HttpClient} from "@angular/common/http";
import {Router} from "@angular/router";
import {stringify} from "querystring";
import {UserPost} from "../examples/model/UserPost";
import {RoleEntity} from "../examples/model/RoleEntity";

@Injectable({
  providedIn: 'root'
})
export class UserService {
  urlGetProfile: string = "http://localhost:8080/api/getprofile/";
  urlLogin: string = "http://localhost:8080/login";
  urlRegister: string = "http://localhost:8080/api/adduser";
  private users: BehaviorSubject<UserJwt[]> = new BehaviorSubject([]);
  private user: BehaviorSubject<UserJwt> = new BehaviorSubject<UserJwt>(new class implements UserJwt {
    id: string;
    token: string;
    type: string;
    userName: string;
    roles: string[];
    avatar: string;
  });
  userProfile: BehaviorSubject<UserPost> = new BehaviorSubject<UserPost>(new class implements UserPost {
    email: string;
    firstName: string;
    id: number;
    lastLogin: number;
    lastName: string;
    mobile: string;
    password: string;
    registeredAt: number;
    roleEntityList: RoleEntity[];
    srcAvatar: string;
    userName: string;
  });
  private message: BehaviorSubject<string> = new BehaviorSubject<string>("");

  constructor(private httpClient: HttpClient,private router: Router) {

  }

  logout() {
    localStorage.removeItem('currentUser');
  }

  getUserInfor() {
   var userName: string = localStorage.getItem('currentUserName');
    alert(userName);
    this.httpClient.get(this.urlGetProfile+userName).subscribe(u => this.userProfile.next(u))
  }

  login(name: string, pass: string) {
    let user: UserJwt = {
      userName: name,
      password: pass
    };
    this.httpClient.post(this.urlLogin,user).subscribe( result => {
      this.user.next(result);
      this.message.next("success");
      localStorage.setItem('currentUser', stringify(this.user.value));
      localStorage.setItem('currentUserName', this.user.value.userName);
      console.log(this.user.getValue());
      this.router.navigate(["/"]);
    },error => {
       console.log(error.status)
      this.message.next(this.loginCatch(error.status));
    })
  }

  getMessage() {
    return this.message;
  }

  loginCatch(s: string): string {
    if (s=="403"){
        return "wrong password or username";}
    if (s=="404"){
        return "dont have this acction link";}else {
      return "down serve backend";
    }

  }

  createUser(user: UserJwt) {
    this.message.next("");

    this.httpClient.post(this.urlRegister, user).subscribe(
        result => {
          console.log(result);
          this.login(user.userName, user.password);
        },
            error => {
          alert(user.userName);
              console.log(error);
            });
  }
  createUserCatch(s: string): string {
    if (s=="403"){
      return "wrong password or username";}
    if (s=="404"){
      return "dont have this acction link";}else {
      return "down serve backend";
    }

  }

  getCurrentUserValue(): UserJwt {
    return this.user.value
  }
}
