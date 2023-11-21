package si.fri.prpo.skupina7.zrna;

import si.fri.prpo.skupina7.dtos.OcenaDto;
import si.fri.prpo.skupina7.entitete.Film;
import si.fri.prpo.skupina7.entitete.Ocena;
import si.fri.prpo.skupina7.entitete.Uporabnik;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.time.Instant;
import java.util.UUID;
import java.util.logging.Logger;

@RequestScoped
public class UpravljanjeOcenZrno {
    private UUID id;

    private Logger log = Logger.getLogger(UpravljanjeOcenZrno.class.getName());

    @PostConstruct
    private void init() {
        id = java.util.UUID.randomUUID();
        log.info("Inicializacija zrna " + UpravljanjeOcenZrno.class.getSimpleName() + " (" + id + ")");
    }

    @PreDestroy
    private void destroy() {
        log.info("Deinicializacija zrna " + UpravljanjeOcenZrno.class.getSimpleName() + " (" + id + ")");
    }

    @Inject
    private OceneZrno oceneZrno;

    @Inject
    private FilmiZrno filmiZrno;

    @Inject
    private UporabnikiZrno uporabnikiZrno;

    @Transactional
    public Ocena dodajOceno(OcenaDto ocenaDto) {

        Film film = filmiZrno.pridobiFilm(ocenaDto.getFilmId());
        if(film == null) {
            log.info("Film ne obstaja.");
            return null;
        }

        Uporabnik uporabnik = uporabnikiZrno.pridobiUporabnika(ocenaDto.getUporabnikId());
        if(uporabnik == null) {
            log.info("Uporabnik ne obstaja.");
            return null;
        }

        Integer ocena1 = ocenaDto.getOcena();
        if(ocena1 < 0 || ocena1 > 10) {
            log.info("Ocena je neveljavna.");
            return null;
        }

        Ocena ocena = new Ocena();
        ocena.setFilm(film);
        ocena.setUporabnik(uporabnik);
        ocena.setOcena(ocena1);
        ocena.setKomentar(ocenaDto.getKomentar());
        ocena.setCasOddaje(Instant.now());

        return oceneZrno.dodajOceno(ocena);
    }
}
