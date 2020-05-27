import { Component, OnInit } from '@angular/core';
import {UserService} from "../../service/user.service";
import {UserPost} from "../model/UserPost";
import {BehaviorSubject} from "rxjs";
import {RoleEntity} from "../model/RoleEntity";
import {ActivatedRoute, UrlSegment} from "@angular/router";
import {PostServiceService} from "../service/post-service.service";
import {Post} from "../model/Post";
import {PostService} from "../../service/post.service";

@Component({
    selector: 'app-profile',
    templateUrl: './profile.component.html',
    styleUrls: ['./profile.component.scss']
})

export class ProfileComponent implements OnInit {
    posts:Post[]=[];
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

    constructor(private userService: UserService,
                private activatedRoute: ActivatedRoute,
                private postServiceService:PostServiceService,
                private postService:PostService)
    {
        this.activatedRoute.params.subscribe((b) => alert(b['id']));
    }

    ngOnInit() {
        this.userService.getUserInfor();
        this.userService.userProfile.subscribe(b => {
            this.userProfile = b;
            this.registeredAt = new Date(this.userProfile.registeredAt).toString();
        });
        this.postServiceService.getPostByUser().subscribe(u => this.posts = u);
    }

    changeStatus(post: Post) {
        if (post.publishedStatus == 0) {
            post.publishedStatus = 1;
        }else {
            post.publishedStatus = 0;
        }
        this.postServiceService.savePost(post);
    }
}
