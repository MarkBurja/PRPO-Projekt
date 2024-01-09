import {Component, OnInit} from '@angular/core';
import {Router} from '@angular/router';

import { Ocena } from './models/ocena';
import { PriporocilaService } from './services/priporocila.service';

@Component({
    moduleId: module.id,
    selector: 'vse-ocene',
    templateUrl: 'ocene.component.html'
})
export class OceneComponent implements OnInit {
    ocene: Ocena[];
    ocena: Ocena;

    constructor(private priporocilaService: PriporocilaService,
                private router: Router) {
    }

    ngOnInit(): void {
        this.getOcene();
        this.router.routeReuseStrategy.shouldReuseRoute = () => false
    }

    getOcene(): void {
        this.priporocilaService
            .getOcene()
            .subscribe(ocene => this.ocene = ocene);
    }

    naPodrobnosti(ocena: Ocena): void {
        this.ocena = ocena;
        this.router.navigate(['/ocene', this.ocena.id]);
    }

    deleteOcena(ocena: Ocena): void {
        this.priporocilaService
            .deleteOcena(ocena.id)
            .subscribe(ocenaId => {
                this.ocene = this.ocene.filter(o => o.id !== ocenaId);
                this.router.navigate(['/ocene'])
            });
    }

    dodajOceno(): void {
        this.router.navigate(['dodajOceno']);
    }

}
