import {UserPost} from "./UserPost";

export interface Media {
    id:number,
    srcMedia:string,
    mediaName:string,
    mediaType:string,
    userByUserId?: UserPost
}