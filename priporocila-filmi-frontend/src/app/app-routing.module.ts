import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';

import {OceneComponent} from './seznami/ocene.component';
import {OcenaPodrobnostiComponent} from './seznami/ocena-podrobnosti.component';
import { OcenaDodajComponent } from './seznami/ocena-dodaj.component';

const routes: Routes = [
    {path: '', redirectTo: '/ocene', pathMatch: 'full'},
    {path: 'ocene', component: OceneComponent},
    {path: 'ocene/:id', component: OcenaPodrobnostiComponent},
    {path: 'dodajOceno', component: OcenaDodajComponent}
];

@NgModule({
    imports: [RouterModule.forRoot(routes, {onSameUrlNavigation: 'reload'})],
    exports: [RouterModule]
})
export class AppRoutingModule {
}
