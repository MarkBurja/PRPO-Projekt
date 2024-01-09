package si.fri.prpo.skupina7.zrna;

import com.kumuluz.ee.rest.beans.QueryParameters;
import com.kumuluz.ee.rest.utils.JPAUtils;
import si.fri.prpo.skupina7.entitete.Ocena;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.persistence.*;
import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

@ApplicationScoped
public class OceneZrno {

    @PersistenceContext(unitName = "priporocila-jpa")
    private EntityManager em;

    private UUID id;

    private Logger log = Logger.getLogger(OceneZrno.class.getName());

    @PostConstruct
    private void init() {
        id = UUID.randomUUID();
        log.info("Inicializacija zrna " + OceneZrno.class.getSimpleName() + " (" + id + ")");
    }

    @PreDestroy
    private void destroy() {
        log.info("Deinicializacija zrna " + OceneZrno.class.getSimpleName() + " (" + id + ")");
    }

    public List<Ocena> pridobiOcene() {
        TypedQuery<Ocena> query = em.createNamedQuery("Ocena.getAll", Ocena.class);
        List<Ocena> results = query.getResultList();
        return results;
    }

    public List<Ocena> pridobiOcene(QueryParameters query) {
        return JPAUtils.queryEntities(em, Ocena.class, query);
    }

    public Long pridobiOceneCount(QueryParameters query) {
        return JPAUtils.queryEntitiesCount(em, Ocena.class, query);
    }

    public Ocena pridobiOceno(int ocenaId) {
        Ocena ocena = em.find(Ocena.class, ocenaId);
        return ocena;
    }

    @Transactional
    public Ocena dodajOceno(Ocena ocena) {
        if(ocena != null) {
            em.persist(ocena);
        }
        return ocena;
    }

    @Transactional
    public Ocena posodobiOceno(int ocenaId, Ocena novaOcena) {
        Ocena ocena = em.find(Ocena.class, ocenaId);
        novaOcena.setId(ocena.getId());
        em.merge(novaOcena);

        return novaOcena;
    }

    @Transactional
    public boolean odstraniOceno(int ocenaId) {
        Ocena ocena = em.find(Ocena.class, ocenaId);

        if(ocena != null) {
            em.remove(ocena);
            return true;
        }

        return false;
    }
}
