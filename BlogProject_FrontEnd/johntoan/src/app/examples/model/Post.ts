import {CommentPost} from './CommentPost';
import {UserPost} from './UserPost';
import {CategoryEntity} from './CategoryEntity';
import {PostLikes} from './PostLikes';
import {TagEntity} from './TagEntity';
import {Media} from "./Media";

export interface Post {
    id?: number,
    title: string,
    publishedStatus?: number,
    publishTime?: number,
    createdAt?: number,
    updatedAt?: number,
    content?: string,
    commentsById?: CommentPost[],
    userByUserId?: UserPost,
    categoryEntityList?: CategoryEntity[],
    tagEntityList?: TagEntity[],
    postLikesById?: PostLikes[],
    mediaList?: Media[];
    postImage?:string;
    userId?:number;
}
