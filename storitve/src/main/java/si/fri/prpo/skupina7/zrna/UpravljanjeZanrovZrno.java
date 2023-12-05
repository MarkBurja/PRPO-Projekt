package si.fri.prpo.skupina7.zrna;

import si.fri.prpo.skupina7.dtos.ZanrDto;
import si.fri.prpo.skupina7.entitete.Zanr;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.UUID;
import java.util.logging.Logger;

@ApplicationScoped
public class UpravljanjeZanrovZrno {
    private UUID id;

    private Logger log = Logger.getLogger(UpravljanjeZanrovZrno.class.getName());

    @PostConstruct
    private void init() {
        id = java.util.UUID.randomUUID();
        log.info("Inicializacija zrna " + UpravljanjeZanrovZrno.class.getSimpleName() + " (" + id + ")");
    }

    @PreDestroy
    private void destroy() {
        log.info("Deinicializacija zrna " + UpravljanjeZanrovZrno.class.getSimpleName() + " (" + id + ")");
    }

    @Inject
    private ZanriZrno zanriZrno;

    @Transactional
    public Zanr dodajZanr(ZanrDto zanrDto) {
        Zanr zanr = new Zanr();
        zanr.setIme(zanrDto.getIme());

        return zanriZrno.dodajZanr(zanr);
    }

    public Zanr posodobiZanr(int id, ZanrDto zanrDto) {
        Zanr zanr = zanriZrno.pridobiZanr(id);

        if(zanr == null) {
            log.info("Zanr ne obstaja.");
            return null;
        }

        zanr.setIme(zanrDto.getIme());

        return zanriZrno.posodobiZanr(id, zanr);
    }
}
