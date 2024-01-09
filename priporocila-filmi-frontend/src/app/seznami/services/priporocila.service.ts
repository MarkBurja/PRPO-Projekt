import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';

import { Ocena } from "../models/ocena";
import { Observable } from 'rxjs';

import { catchError } from 'rxjs/operators';
import {Film} from "../models/film";
import {OcenaDto} from "../models/ocena-dto";

@Injectable()
export class PriporocilaService {

    private headers = new HttpHeaders({'Content-Type': 'application/json'});
    private url = 'http://localhost:8080/v1';

    constructor(private http: HttpClient) {
    }

    getOcene(): Observable<Ocena[]> {
        return this.http.get<Ocena[]>(`${this.url}/ocene`)
                        .pipe(catchError(this.handleError));
    }

    getOcena(id: number): Observable<Ocena> {
        const url = `${this.url}/ocene/${id}`;
        return this.http.get<Ocena>(url)
                        .pipe(catchError(this.handleError));
    }

    getFilmi(): Observable<Film[]> {
        return this.http.get<Film[]>(`${this.url}/filmi`)
            .pipe(catchError(this.handleError));
    }

    deleteOcena(id: number): Observable<number> {
        const url = `${this.url}/ocene/${id}`;
        return this.http.delete<number>(url, {headers: this.headers})
                        .pipe(catchError(this.handleError));
    }

    createOcena(ocena: OcenaDto): Observable<Ocena> {
        return this.http.post<OcenaDto>(`${this.url}/ocene`, JSON.stringify(ocena), {headers: this.headers})
                        .pipe(catchError(this.handleError));
    }

    private handleError(error: any): Promise<any> {
        console.error('Prišlo je do napake', error);
        return Promise.reject(error.message || error);
    }
}

