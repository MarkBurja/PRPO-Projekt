package si.fri.prpo.skupina7.zrna;

import si.fri.prpo.skupina7.entitete.Uporabnik;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.persistence.*;
import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

@ApplicationScoped
public class UporabnikiZrno {

    @PersistenceContext(unitName = "priporocila-jpa")
    private EntityManager em;

    private UUID id;

    private Logger log = Logger.getLogger(UporabnikiZrno.class.getName());

    @PostConstruct
    private void init() {
        id = java.util.UUID.randomUUID();
        log.info("Inicializacija zrna " + UporabnikiZrno.class.getSimpleName() + " (" + id + ")");
    }

    @PreDestroy
    private void destroy() {
        log.info("Deinicializacija zrna " + UporabnikiZrno.class.getSimpleName() + " (" + id + ")");
    }

    public List<Uporabnik> pridobiUporabnike() {
        TypedQuery<Uporabnik> query = em.createNamedQuery("Uporabnik.getAll", Uporabnik.class);
        List<Uporabnik> results = query.getResultList();
        return results;
    }

    public Uporabnik pridobiUporabnika(int uporabnikId) {
        Uporabnik uporabnik = em.find(Uporabnik.class, uporabnikId);
        return uporabnik;
    }

    @Transactional
    public Uporabnik dodajUporabnika(Uporabnik uporabnik) {
        if(uporabnik != null) {
            em.persist(uporabnik);
        }
        return uporabnik;
    }

    @Transactional
    public Uporabnik posodobiUporabnika(int uporabnikId, Uporabnik noviUporabnik) {
        Uporabnik uporabnik = em.find(Uporabnik.class, uporabnikId);
        noviUporabnik.setId(uporabnik.getId());
        em.merge(noviUporabnik);

        return noviUporabnik;
    }

    @Transactional
    public boolean odstraniUporabnika(int uporabnikId) {
        Uporabnik uporabnik = em.find(Uporabnik.class, uporabnikId);

        if(uporabnik != null) {
            em.remove(uporabnik);
            return true;
        }

        return false;
    }
}
