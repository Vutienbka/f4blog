export interface UserJwt {
    id?: string;
    token?: string;
    type?: string;
    userName?: string;
    roles?: string[];
    avatar?: string;
    password?: string;
    email?: string;
}