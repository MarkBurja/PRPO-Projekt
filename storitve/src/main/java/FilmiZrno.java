import javax.enterprise.context.ApplicationScoped;
import javax.persistence.*;
import java.util.List;

@ApplicationScoped
public class FilmiZrno {

    @PersistenceContext(unitName = "priporocila-jpa")
    private EntityManager em;

    public List<Film> getFilmi() {
        TypedQuery<Film> query = em.createNamedQuery("Film.getAll", Film.class);
        List<Film> results = query.getResultList();
        return results;
    }
}
