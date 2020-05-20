import { Injectable } from '@angular/core';
import {BehaviorSubject, Observable} from "rxjs";
import {UserJwt} from "../examples/model/UserJwt";
import {HttpClient} from "@angular/common/http";
import {Router} from "@angular/router";

@Injectable({
  providedIn: 'root'
})
export class UserService {
  urlLogin: string = "http://localhost:8080/Gradle___org_example___Blog_Project_1_0_SNAPSHOT_war/login";
  urlRegister: string = "http://localhost:8080/Gradle___org_example___Blog_Project_1_0_SNAPSHOT_war/";
  private users: BehaviorSubject<UserJwt[]> = new BehaviorSubject([]);
  private user: BehaviorSubject<UserJwt> = new BehaviorSubject<UserJwt>(new class implements UserJwt {
    id: string;
    token: string;
    type: string;
    userName: string;
    roles: string[];
    avatar: string;
  })
  private message: BehaviorSubject<string> = new BehaviorSubject<string>("");

  constructor(private httpClient: HttpClient,private router: Router) {

  }

  login(name: string, pass: string) {
    let user: UserJwt = {
      userName: name,
      password: pass
    };
    this.httpClient.post(this.urlLogin,user).subscribe( result => {
      this.user.next(result);
      this.message.next("success");
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

  createUser(name: string, pass: string, email: string) {
    this.message.next("");
    let user: UserJwt = {
      userName: name,
      password: pass,
      email:email
    };

    this.httpClient.post(this.urlRegister,user)


    this.login(name, pass);
  }
}
