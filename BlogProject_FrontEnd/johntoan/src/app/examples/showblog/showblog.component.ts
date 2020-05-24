import {Component, OnInit} from '@angular/core';
import {PostServiceService} from '../service/post-service.service';
import {Post} from '../model/Post';
import {UserPost} from '../model/UserPost';
import {ActivatedRoute, Router} from "@angular/router";
import {CategoryEntity} from "../model/CategoryEntity";
import {CommentPost} from "../model/CommentPost";
import {PostLikes} from "../model/PostLikes";
import {TagEntity} from "../model/TagEntity";
import {PostService} from "../../service/post.service";
import {HttpClient} from "@angular/common/http";
import * as DecoupledEditor from '@ckeditor/ckeditor5-build-decoupled-document';
@Component({
    selector: 'app-showblog',
    templateUrl: './showblog.component.html',
    styleUrls: ['./showblog.component.css']
})
export class ShowblogComponent implements OnInit {

    postList: Post[]=[];

    public Editor = DecoupledEditor;
    private post: Post = new class implements Post {
        categoryEntityList: CategoryEntity[];
        commentsById: CommentPost[];
        content: string;
        createdAt: number;
        id: number;
        postLikesById: PostLikes[];
        publishTime: number;
        publishedStatus: boolean;
        tagEntityList: TagEntity[];
        title: string;
        updatedAt: number;
        userByUserId: UserPost;
    };

    constructor(private postService: PostService,
                private route:ActivatedRoute,
                private router: Router,
                private httpClient:HttpClient) {
    }

    private id:number;
    ngOnInit(): void {
        this.route.paramMap.subscribe(param => {
            this.id = +param.get('id');
            this.post = this.postService.getOnePost(this.id);
            if(this.post===undefined){
                this.postService.fetchAllPostFromAPI().subscribe((resJson) => {
                    this.postService.postList = resJson;
                    this.post = this.postService.getOnePost(this.id);
                    console.log(this.post);
                });
            }
        });
    }

    editPost(post:Post){
        this.router.navigate(['editPost',post.id]);

    }

}
