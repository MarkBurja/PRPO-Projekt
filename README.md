# PRIMERI KLICEV
- **GET** http://localhost:8080/v1/uporabniki?offset=3&limit=5
- **GET** http://localhost:8080/v1/filmi?order=leto ASC
- **GET** http://localhost:8080/v1/filmi?where=rating:gte:9
- **GET** http://localhost:8080/v1/uporabniki?filter=spol:EQ:F&order=starost DESC
- **GET** http://localhost:8080/v1/filmi?filter=zanr.ime:EQ:komedija&order=naslov ASC
- **GET** http://localhost:8080/v1/ocene?where=film.naslov:LIKE:'%Shrek%'&order=ocena DESC&limit=1

- **POST** http://localhost:8080/v1/uporabniki
{
    "email": null,
    "geslo": "213Sdedea",
    "ime": "Klemen",
    "priimek": "Bračko",
    "spol": "M",
    "starost": 35,
    "uporabniskoIme": null
}