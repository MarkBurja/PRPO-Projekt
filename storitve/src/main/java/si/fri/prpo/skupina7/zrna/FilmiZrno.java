package si.fri.prpo.skupina7.zrna;

import si.fri.prpo.skupina7.anotacije.BeleziKlice;
import si.fri.prpo.skupina7.entitete.Film;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.persistence.*;
import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

@ApplicationScoped
public class FilmiZrno {

    @PersistenceContext(unitName = "priporocila-jpa")
    private EntityManager em;

    private UUID id;

    private Logger log = Logger.getLogger(FilmiZrno.class.getName());

    @PostConstruct
    private void init() {
        id = java.util.UUID.randomUUID();
        log.info("Inicializacija zrna " + FilmiZrno.class.getSimpleName() + " (" + id + ")");
    }

    @PreDestroy
    private void destroy() {
        log.info("Deinicializacija zrna " + FilmiZrno.class.getSimpleName() + " (" + id + ")");
    }

    @BeleziKlice
    public List<Film> pridobiFilme() {
        TypedQuery<Film> query = em.createNamedQuery("Film.getAll", Film.class);
        List<Film> results = query.getResultList();
        return results;
    }

    @BeleziKlice
    public Film pridobiFilm(int filmId) {
        Film film = em.find(Film.class, filmId);
        return film;
    }

    @Transactional
    public Film dodajFilm(Film film) {
        if(film != null) {
            em.persist(film);
        }
        return film;
    }

    @Transactional
    public Film posodobiFilm(int filmId, Film noviFilm) {
        Film film = em.find(Film.class, filmId);
        noviFilm.setId(film.getId());
        em.merge(noviFilm);

        return noviFilm;
    }

    @Transactional
    public boolean odstraniFilm(int filmId) {
        Film film = em.find(Film.class, filmId);

        if(film != null) {
            em.remove(film);
            return true;
        }

        return false;
    }
}
