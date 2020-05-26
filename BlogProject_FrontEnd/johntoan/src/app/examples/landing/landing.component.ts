import {Component, OnInit} from '@angular/core';
import {Post} from '../model/Post';
import {HttpClient} from '@angular/common/http';
import {PostServiceService} from '../service/post-service.service';
import {UserService} from "../../service/user.service";
import {PostService} from "../../service/post.service";
import {Router} from "@angular/router";
import {Media} from "../model/Media";
import {MediaService} from "../../service/media.service";

@Component({
    selector: 'app-landing',
    templateUrl: './landing.component.html',
    styleUrls: ['./landing.component.scss']
})

export class LandingComponent implements OnInit {
    p: number = 1;
    listPost: Post[]=[];
    mediaList:Media[] = [];
    focus: any;
    focus1: any;
    introduce: string[];

    constructor(private http: HttpClient,
                private postService: PostService,
                private mediaService:MediaService,
                private userService:UserService,
                private router:Router) {
    }

    ngOnInit() {
        this.getAllPost();
    }

    getAllPost(){
        this.postService.fetchAllPostFromAPI().subscribe((resJson) => {
            this.listPost = resJson;
            console.log(this.listPost);
        });
        // this.getAllMedias();
    }

    // getAllMedias(){
    //     this.mediaService.fetchAllMediaFromAPI().subscribe((resJson) => {
    //         this.mediaList = resJson;
    //         console.log(this.mediaList);
    //     });
    // }

    logout() {
        this.userService.logout();
    }

    addPostToService(post: Post) {
        this.router.navigate(['showblog', post.id]);
        // this.postService.setPost(post);
    }
}


// sliceConten(conten: string) {
//     if (conten.length > 400) {
//         return conten.slice(0, 400) + '...';
//     } else {
//         return conten;
//     }
// }
