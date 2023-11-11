INSERT INTO uporabnik (uporabniskoime, email, geslo, ime, priimek, starost, spol) VALUES ('petrakos', 'petra.kos@hotmail.com', 'wdscxy,', 'Petra', 'Kos', 23, 'F');
INSERT INTO uporabnik (uporabniskoime, email, geslo, ime, priimek, starost, spol) VALUES ('mihanovak', 'miha.novak@gmail.com', '123456', 'Miha', 'Novak', 41 , 'M');
INSERT INTO uporabnik (uporabniskoime, email, geslo, ime, priimek, starost, spol) VALUES ('jakec123', 'jakakovac@gmail.com', 'aef23fdycx', 'Jaka', 'Kovač', 19 , 'M');
INSERT INTO zanr (ime) VALUES ('komedija');
INSERT INTO zanr (ime) VALUES ('drama');
INSERT INTO zanr (ime) VALUES ('akcija');
INSERT INTO film (naslov, opis, leto, rating, zanr) VALUES ('Shrek', 'Opis filma Shrek.', 2001, 8, 1);
INSERT INTO film (naslov, opis, leto, rating, zanr) VALUES ('Shrek 2', 'Opis filma Shrek 2.', 2004, 7, 1);
INSERT INTO film (naslov, opis, leto, rating, zanr) VALUES ('Forrest Gump', 'Opis filma.', 1994, 9, 2);
INSERT INTO film (naslov, opis, leto, rating, zanr) VALUES ('The Lord of the Rings: The Return of the King', 'Opis filma.', 2003, 9, 3);
INSERT INTO ocena (uporabnik, film, ocena, komentar, casoddaje) VALUES (3, 1, 1, 'FUJ!', '2023-11-11 02:01:32')