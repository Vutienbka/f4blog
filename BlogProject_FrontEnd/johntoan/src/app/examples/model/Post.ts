import {CommentPost} from './CommentPost';
import {UserPost} from './UserPost';
import {CategoryEntity} from './CategoryEntity';
import {PostLikes} from './PostLikes';
import {TagEntity} from './TagEntity';

export interface Post {
    id?: number,
    title: string,
    publishedStatus?: boolean,
    publishTime?: number,
    createdAt?: number,
    updatedAt?: number,
    content?: string,
    commentsById?: CommentPost[],
    userByUserId?: UserPost,
    categoryEntityList?: CategoryEntity[],
    tagEntityList?: TagEntity[],
    postLikesById?: PostLikes[]
}
