import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';

import { Observable } from 'rxjs';

import { catchError } from 'rxjs/operators';
import {Uporabnik} from "../models/uporabnik";

@Injectable()
export class PrijavaService {

    private headers = new HttpHeaders({'Content-Type': 'application/json'});
    private url = 'http://localhost:8081/v1';

    constructor(private http: HttpClient) {
    }

    prijavaUporabnika(uporabnik: Uporabnik): Observable<Uporabnik> {
        return this.http.post<Uporabnik>(`${this.url}/prijavaUporabnika`, JSON.stringify(uporabnik), {headers: this.headers})
            .pipe(catchError(this.handleError));
    }

    private handleError(error: any): Promise<any> {
        console.error('Prišlo je do napake', error);
        return Promise.reject(error.message || error);
    }
}
