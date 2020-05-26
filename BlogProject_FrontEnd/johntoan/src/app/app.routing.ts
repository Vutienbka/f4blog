import { NgModule } from '@angular/core';
import { CommonModule, } from '@angular/common';
import { BrowserModule  } from '@angular/platform-browser';
import { Routes, RouterModule } from '@angular/router';

import { ProfileComponent } from './examples/profile/profile.component';
import { SigninComponent } from './examples/signin/signin.component';
import { LandingComponent } from './examples/landing/landing.component';
import {RegisterComponent} from './examples/register/register.component';
import {DashboardComponent} from './components_user/dashboard/dashboard.component';
import {ShowblogComponent} from './examples/showblog/showblog.component';
import {IndexUserComponent} from './examples/index-user/index-user.component';
import {AboutmeComponent} from './examples/aboutme/aboutme.component';
import {CreatePostComponent} from './examples/create-post/create-post.component';
import {AuthGuard} from "./examples/guards/auth.guard";
import {EditPostComponent} from "./examples/edit-post/edit-post.component";

const routes: Routes = [
    { path: '', redirectTo: 'home', pathMatch: 'full' },
    { path: 'home',             component: LandingComponent },
    // { path: 'profile/:id',     component: ProfileComponent,canActivate: [AuthGuard]},
    { path: 'profile/:id',     component: ProfileComponent},
    { path: 'profile',     component: ProfileComponent,canActivate: [AuthGuard]},
    { path: 'login',           component: SigninComponent },
    { path: 'landing',          component: LandingComponent},
    { path: 'register',          component: RegisterComponent },
    { path: 'showblog/:id',          component: ShowblogComponent},
    { path: 'index',          component: IndexUserComponent },
    { path: 'aboutme',          component: AboutmeComponent,canActivate: [AuthGuard]},
    { path: 'createPost',          component: CreatePostComponent,canActivate: [AuthGuard] },
    { path: 'editPost/:id',          component: EditPostComponent }

];

@NgModule({
  imports: [
    CommonModule,
    BrowserModule,
    RouterModule.forRoot(routes, {
        onSameUrlNavigation: 'reload'
    })
  ],
  exports: [
  ],
})
export class AppRoutingModule { }
