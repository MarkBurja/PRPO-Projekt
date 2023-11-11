import javax.persistence.*;

@Entity(name = "zanr")
@NamedQueries(value =
        {
                @NamedQuery(name = "Zanr.getAll", query = "SELECT z FROM zanr z")
        })
public class Zanr {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String ime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    @Override
    public String toString() {
        return ime;
    }
}
