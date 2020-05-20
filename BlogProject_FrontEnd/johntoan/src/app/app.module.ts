import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {NgbModule} from '@ng-bootstrap/ng-bootstrap';
import {RouterModule} from '@angular/router';
import {AppRoutingModule} from './app.routing';

import {AppComponent} from './app.component';
import {NavbarComponent} from './shared/navbar/navbar.component';
import {FooterComponent} from './shared/footer/footer.component';

import {ExamplesModule} from './examples/examples.module';
import { DashboardComponent } from './components_user/dashboard/dashboard.component';
import {HttpClientModule} from "@angular/common/http";

@NgModule({
    declarations: [
        AppComponent,
        NavbarComponent,
        FooterComponent,
        DashboardComponent,
    ],
    imports: [
        AppRoutingModule,FormsModule,ReactiveFormsModule,
        BrowserModule,
        NgbModule,
        FormsModule,
        RouterModule,
        ExamplesModule,
        AppRoutingModule,
        HttpClientModule
    ],
    providers: [],
    bootstrap: [AppComponent]
})
export class AppModule {
}
