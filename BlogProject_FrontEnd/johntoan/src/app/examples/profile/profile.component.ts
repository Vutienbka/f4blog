import { Component, OnInit } from '@angular/core';
import {UserService} from "../../service/user.service";
import {UserPost} from "../model/UserPost";
import {BehaviorSubject} from "rxjs";
import {RoleEntity} from "../model/RoleEntity";
import {ActivatedRoute, UrlSegment} from "@angular/router";

@Component({
    selector: 'app-profile',
    templateUrl: './profile.component.html',
    styleUrls: ['./profile.component.scss']
})

export class ProfileComponent implements OnInit {
    userProfile: UserPost = new class implements UserPost {
        email: string ="";
        firstName: string="";
        id: number=0;
        lastLogin: number=0;
        lastName: string="";
        mobile: string;
        password: string;
        registeredAt: number;
        roleEntityList: RoleEntity[];
        srcAvatar: string;
        userName: string;
    };
    registeredAt: string = "";

    constructor(private userService: UserService,private activatedRoute: ActivatedRoute) {
        this.activatedRoute.params.subscribe((b) => console.log(b['id']));
    }

    ngOnInit() {this.userService.getUserInfor();
        this.userService.userProfile.subscribe(b => {
            this.userProfile = b;
            this.registeredAt = new Date(this.userProfile.registeredAt).toString();
        });
    }

}
