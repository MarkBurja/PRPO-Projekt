package si.fri.prpo.skupina7.zrna;

import si.fri.prpo.skupina7.entitete.Zanr;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.persistence.*;
import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

@ApplicationScoped
public class ZanriZrno {

    @PersistenceContext(unitName = "priporocila-jpa")
    private EntityManager em;

    private UUID id;

    private Logger log = Logger.getLogger(ZanriZrno.class.getName());

    @PostConstruct
    private void init() {
        id = java.util.UUID.randomUUID();
        log.info("Inicializacija zrna " + ZanriZrno.class.getSimpleName() + " (" + id + ")");
    }

    @PreDestroy
    private void destroy() {
        log.info("Deinicializacija zrna " + ZanriZrno.class.getSimpleName() + " (" + id + ")");
    }

    public List<Zanr> pridobiZanre() {
        TypedQuery<Zanr> query = em.createNamedQuery("Zanr.getAll", Zanr.class);
        List<Zanr> results = query.getResultList();
        return results;
    }

    public Zanr pridobiZanr(int zanrId) {
        Zanr zanr = em.find(Zanr.class, zanrId);
        return zanr;
    }

    @Transactional
    public Zanr dodajZanr(Zanr zanr) {
        if(zanr != null) {
            em.persist(zanr);
        }
        return zanr;
    }

    @Transactional
    public void posodobiZanr(int zanrId, Zanr noviZanr) {
        Zanr zanr = em.find(Zanr.class, zanrId);
        noviZanr.setId(zanr.getId());
        em.merge(noviZanr);
    }

    @Transactional
    public boolean odstraniZanr(int zanrId) {
        Zanr zanr = em.find(Zanr.class, zanrId);

        if(zanr != null) {
            em.remove(zanr);
            return true;
        }

        return false;
    }
}