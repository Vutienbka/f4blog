import {Injectable} from '@angular/core';
import {Post} from '../model/Post';
import {BehaviorSubject} from 'rxjs';
import {CommentPost} from '../model/CommentPost';
import {CategoryEntity} from '../model/CategoryEntity';
import {PostLikes} from '../model/PostLikes';
import {TagEntity} from '../model/TagEntity';
import {UserPost} from '../model/UserPost';


@Injectable({
    providedIn: 'root'
})
export class PostServiceService {
    urlGetPostByUser: string = "";
    posts: BehaviorSubject<Post[]> = new BehaviorSubject<Post[]>([]);
    post: BehaviorSubject<Post> = new BehaviorSubject<Post>(new class implements Post {
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
    });

    constructor() {
    }

    getPost() {
        return this.post;
    }

    setPost(post2: Post) {
        this.post.next(post2);
    }

    getAllPostByUser() {

    }
}
