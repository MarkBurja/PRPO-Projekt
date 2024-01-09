import { Uporabnik } from './uporabnik';
import { Film } from './film';

export class Ocena {
    id: number;
    uporabnik: Uporabnik;
    film: Film;
    ocena: number;
    komentar: string;
    casOddaje: string;
}