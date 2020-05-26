import { Component, OnInit } from '@angular/core';
import {UserService} from "../../service/user.service";
import {EmailValidator, FormControl, FormGroup, Validators} from "@angular/forms";
import {UserJwt} from "../model/UserJwt";

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {
  userRegisterForm: FormGroup;
  message: string;
  test : Date = new Date();
  focus;
  focus1;
  constructor(private userService:UserService) {
    this.userRegisterForm = new FormGroup({
      userName: new FormControl('',[Validators.required,Validators.maxLength(10),Validators.minLength(5)]),
      email: new FormControl('',[Validators.required,Validators.email]),
      password: new FormControl('',[Validators.required,Validators.maxLength(15),Validators.minLength(6)])
        }
    )
  }

  ngOnInit(): void {
  }

  save() {
    let confirmPassword = (document.getElementById('repassword') as HTMLInputElement).value;
    let status = true;

    switch (status) {
      case this.userRegisterForm.get('userName').getError('required'):
        this.message = "user name is required";
        status = false;
        break;
      case (this.userRegisterForm.get('userName').getError('minlength') !=null || this.userRegisterForm.get('userName').getError('maxlength') !=null):
        this.message = "user name has min length is 5 and max length is 10";
        status = false;
        break;
      case this.userRegisterForm.get('email').getError('email'):
        this.message = "please enter correct email";
        status = false;
        break;
      case this.userRegisterForm.get('email').getError('required'):
        this.message = "email is required";
        status = false;
        break;
      case this.userRegisterForm.get('password').getError('required'):
        this.message = "password required";
        status = false;
        break;
      // case (this.userRegisterForm.get('password').getError('minlength').actualLength<6||this.userRegisterForm.get('password').getError('maxlength').actualLength>15):
      //   this.message = "user name has min length is 6 and max length is 15";
      //   status = false;
      //   break;
      case confirmPassword != this.userRegisterForm.value.password:
        this.message = "please re enter password";
        status = false;
        break;
    }

    if (status) {
      this.userService.getMessage().subscribe(m => this.message = m);
      let user: UserJwt = this.userRegisterForm.value;
      this.userService.createUser(user);
    }
  }
}
