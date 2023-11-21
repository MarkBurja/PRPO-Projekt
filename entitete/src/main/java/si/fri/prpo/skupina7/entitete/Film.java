package si.fri.prpo.skupina7.entitete;

import javax.persistence.*;
import java.util.List;

@Entity(name = "film")
@NamedQueries(value =
        {
                @NamedQuery(name = "Film.getAll", query = "SELECT f FROM film f"),
                @NamedQuery(name = "Film.getPerfect", query = "SELECT f FROM film f WHERE f.rating = 10"),
                @NamedQuery(name = "Film.getBetter", query = "SELECT f FROM film f WHERE f.rating >= :rating"),
                @NamedQuery(name = "Film.getWorse", query = "SELECT f FROM film f WHERE f.rating < :rating"),
                @NamedQuery(name = "Film.getNewer", query = "SELECT f FROM film f WHERE f.leto >= :leto"),
                @NamedQuery(name = "Film.getOlder", query = "SELECT f FROM film f WHERE f.leto < :leto"),
                @NamedQuery(name = "Film.getFilm", query = "SELECT f FROM film f WHERE f.naslov = :naslov")
        })
public class Film {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String naslov;

    private String opis;

    private Integer leto;

    private Integer rating;

    @ManyToOne
    @JoinColumn(name = "zanr", referencedColumnName = "id")
    private Zanr zanr;

    @OneToMany(mappedBy = "film", cascade = CascadeType.ALL)
    private List<Ocena> ocene;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNaslov() {
        return naslov;
    }

    public void setNaslov(String naslov) {
        this.naslov = naslov;
    }

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    public Integer getLeto() {
        return leto;
    }

    public void setLeto(Integer leto) {
        this.leto = leto;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public Zanr getZanr() {
        return zanr;
    }

    public void setZanr(Zanr zanr) {
        this.zanr = zanr;
    }

    public List<Ocena> getOcene() {
        return ocene;
    }

    public void setOcene(List<Ocena> ocene) {
        this.ocene = ocene;
    }

    @Override
    public String toString() {
        return naslov + ", " + opis + ", " + leto + ", " + rating + ", " + zanr.getIme();
    }
}
