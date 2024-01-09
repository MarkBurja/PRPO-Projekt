import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';
import {HttpClientModule} from '@angular/common/http';
import {FormsModule} from '@angular/forms';

import {AppRoutingModule} from './app-routing.module';

import {AppComponent} from './app.component';
import {OceneComponent} from './seznami/ocene.component';
import {OcenaDodajComponent} from './seznami/ocena-dodaj.component';
import {OcenaPodrobnostiComponent} from './seznami/ocena-podrobnosti.component';
import {PriporocilaService} from "./seznami/services/priporocila.service";
import {PrijavaService} from "./seznami/services/prijava.service";



@NgModule({
    imports: [
        BrowserModule,
        HttpClientModule,
        AppRoutingModule,
        FormsModule
    ],
    declarations: [
        AppComponent,
        OceneComponent,
        OcenaPodrobnostiComponent,
        OcenaDodajComponent
    ],
    providers: [PriporocilaService, PrijavaService],
    bootstrap: [AppComponent]
})
export class AppModule {
}

