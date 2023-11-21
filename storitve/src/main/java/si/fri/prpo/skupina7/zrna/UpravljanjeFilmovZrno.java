package si.fri.prpo.skupina7.zrna;

import si.fri.prpo.skupina7.dtos.FilmDto;
import si.fri.prpo.skupina7.entitete.Film;
import si.fri.prpo.skupina7.entitete.Zanr;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.UUID;
import java.util.logging.Logger;

@ApplicationScoped
public class UpravljanjeFilmovZrno {

    private UUID id;

    private Logger log = Logger.getLogger(UpravljanjeFilmovZrno.class.getName());

    @PostConstruct
    private void init() {
        id = java.util.UUID.randomUUID();
        log.info("Inicializacija zrna " + UpravljanjeFilmovZrno.class.getSimpleName() + " (" + id + ")");
    }

    @PreDestroy
    private void destroy() {
        log.info("Deinicializacija zrna " + UpravljanjeFilmovZrno.class.getSimpleName() + " (" + id + ")");
    }

    @Inject
    private FilmiZrno filmiZrno;

    @Inject
    private ZanriZrno zanriZrno;

    @Transactional
    public Film dodajFilm(FilmDto filmDto) {

        Zanr zanr = zanriZrno.pridobiZanr(filmDto.getZanrId());

        if(zanr == null) {
            log.info("Zanr ne obstaja.");
            return null;
        }

        Film film = new Film();
        film.setNaslov(filmDto.getNaslov());
        film.setOpis(filmDto.getOpis());
        film.setLeto(filmDto.getLeto());
        film.setRating(filmDto.getRating());
        film.setZanr(zanr);

        return filmiZrno.dodajFilm(film);
    }

}
