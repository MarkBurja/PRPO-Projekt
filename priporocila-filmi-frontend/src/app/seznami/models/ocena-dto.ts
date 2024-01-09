import { Uporabnik } from './uporabnik';
import { Film } from './film';

export class OcenaDto {
    id: number;
    uporabnikId: number;
    filmId: number;
    ocena: number;
    komentar: string;
    casOddaje: string;
}