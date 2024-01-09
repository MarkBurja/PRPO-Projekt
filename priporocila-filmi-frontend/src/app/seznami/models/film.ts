import { Zanr } from './zanr';

export class Film {
    id: number;
    naslov: string;
    opis: string;
    leto: number;
    rating: number;
    zanr: Zanr;
}