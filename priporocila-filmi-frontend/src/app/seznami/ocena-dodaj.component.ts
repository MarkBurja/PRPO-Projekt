import {Component, OnInit} from '@angular/core';
import {Router, Params, ActivatedRoute} from '@angular/router';

import {PriporocilaService} from './services/priporocila.service';
import { Ocena } from './models/ocena';
import { switchMap } from 'rxjs/operators';
import {PrijavaService} from "./services/prijava.service";
import {Film} from "./models/film";
import {Uporabnik} from "./models/uporabnik";
import {OcenaDto} from "./models/ocena-dto";

@Component({
    moduleId: module.id,
    selector: 'dodaj-oceno',
    templateUrl: 'ocena-dodaj.component.html'
})
export class OcenaDodajComponent implements OnInit {

    ocena: OcenaDto = new OcenaDto;
    prijavljenUporabnik: Uporabnik = new Uporabnik();
    uporabnik: Uporabnik;
    filmi: Film[];

    constructor(private priporocilaService: PriporocilaService,
                private prijavaService: PrijavaService,
                private router: Router,
                private route: ActivatedRoute) {
    }

    ngOnInit() {
        this.getFilmi()
      }

    getFilmi(): void {
        this.priporocilaService
            .getFilmi()
            .subscribe(filmi => this.filmi = filmi);
    }

    submitForm(): void {
        this.prijavaService
            .prijavaUporabnika(this.prijavljenUporabnik)
            .subscribe(uporabnik => {
                this.uporabnik = uporabnik
                this.ocena.uporabnikId = this.uporabnik.id
                this.priporocilaService.createOcena(this.ocena)
                    .subscribe(() => this.router.navigate(['/ocene']));
            })
    }

    nazaj(): void {
        this.router.navigate(['/ocene']);
    }

}
