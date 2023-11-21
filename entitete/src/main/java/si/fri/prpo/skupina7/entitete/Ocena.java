package si.fri.prpo.skupina7.entitete;

import javax.persistence.*;
import java.time.Instant;

@Entity(name = "ocena")
@NamedQueries(value =
        {
                @NamedQuery(name = "Ocena.getAll", query = "SELECT o FROM ocena o")
        })
public class Ocena {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "uporabnik", referencedColumnName = "id")
    private Uporabnik uporabnik;

    @ManyToOne
    @JoinColumn(name = "film", referencedColumnName = "id")
    private Film film;

    private Integer ocena;

    private String komentar;

    private Instant casOddaje;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Uporabnik getUporabnik() {
        return uporabnik;
    }

    public void setUporabnik(Uporabnik uporabnik) {
        this.uporabnik = uporabnik;
    }

    public Film getFilm() {
        return film;
    }

    public void setFilm(Film film) {
        this.film = film;
    }

    public Integer getOcena() {
        return ocena;
    }

    public void setOcena(Integer ocena) {
        this.ocena = ocena;
    }

    public String getKomentar() {
        return komentar;
    }

    public void setKomentar(String komentar) {
        this.komentar = komentar;
    }

    public Instant getCasOddaje() {
        return casOddaje;
    }

    public void setCasOddaje(Instant casOddaje) {
        this.casOddaje = casOddaje;
    }

    @Override
    public String toString() {
        return uporabnik.getUporabniskoIme() + ", " + film.getNaslov() + ", " + ocena + ", " + komentar + ", " + casOddaje;
    }
}
