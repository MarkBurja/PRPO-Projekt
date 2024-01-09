import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Params, Router} from '@angular/router';
import {Location} from '@angular/common';

import { switchMap } from 'rxjs/operators';

import {PriporocilaService} from './services/priporocila.service';
import {Ocena} from "./models/ocena";

@Component({
    moduleId: module.id,
    selector: 'ocena-podrobnosti',
    templateUrl: 'ocena-podrobnosti.component.html'
})
export class OcenaPodrobnostiComponent implements OnInit {
    ocena: Ocena;

    constructor(private priporocilaService: PriporocilaService,
                private route: ActivatedRoute,
                private location: Location,
                private router: Router) {
    }

    ngOnInit(): void {
       this.route.params.pipe(
            switchMap((params: Params) => this.priporocilaService.getOcena(+params['id'])))
            .subscribe(ocena => this.ocena = ocena);
    }

    nazaj(): void {
        this.router.navigate(['ocene']);
    }
}
