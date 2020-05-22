import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';

import { LandingComponent } from './landing/landing.component';
import { ProfileComponent } from './profile/profile.component';
import { SigninComponent } from './signin/signin.component';
import { RegisterComponent } from './register/register.component';
import {RouterModule} from '@angular/router';
import { ShowblogComponent } from './showblog/showblog.component';
import { IndexUserComponent } from './index-user/index-user.component';
import { AboutmeComponent } from './aboutme/aboutme.component';
import {HttpClientModule} from "@angular/common/http";
import { CreatePostComponent } from './create-post/create-post.component';
import {CKEditorModule} from "@ckeditor/ckeditor5-angular";

@NgModule({
    imports: [
        CommonModule,
        FormsModule, ReactiveFormsModule,
        NgbModule,
        RouterModule,
        HttpClientModule, CKEditorModule
    ],
    declarations: [
        LandingComponent,
        SigninComponent,
        ProfileComponent,
        RegisterComponent,
        ShowblogComponent,
        IndexUserComponent,
        AboutmeComponent,
        CreatePostComponent
    ]
})
export class ExamplesModule { }
