import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';
import {FormArray, FormsModule, ReactiveFormsModule} from '@angular/forms';
import {NgbModule} from '@ng-bootstrap/ng-bootstrap';
import {RouterModule} from '@angular/router';
import {AppRoutingModule} from './app.routing';

import {AppComponent} from './app.component';
import {NavbarComponent} from './shared/navbar/navbar.component';
import {FooterComponent} from './shared/footer/footer.component';
import {ExamplesModule} from './examples/examples.module';
import { DashboardComponent } from './components_user/dashboard/dashboard.component';
import {HTTP_INTERCEPTORS, HttpClientModule} from "@angular/common/http";
import {InterceptorService} from "./service/interceptor.service";
import {PostService} from "./service/post.service";
import { CKEditorModule } from '@ckeditor/ckeditor5-angular';
import {MediaService} from "./service/media.service";
import {NgxPaginationModule} from "ngx-pagination";

@NgModule({
    declarations: [
        AppComponent,
        NavbarComponent,
        FooterComponent,
        DashboardComponent
    ],
    imports: [
        AppRoutingModule,
        FormsModule,
        BrowserModule,
        NgbModule,
        RouterModule,
        ExamplesModule,
        AppRoutingModule,
        HttpClientModule,
        ReactiveFormsModule,
        CKEditorModule,
        NgxPaginationModule

    ],
    providers: [{ provide: HTTP_INTERCEPTORS, useClass: InterceptorService, multi: true }, PostService,MediaService],
    bootstrap: [AppComponent]
})
export class AppModule {
}
