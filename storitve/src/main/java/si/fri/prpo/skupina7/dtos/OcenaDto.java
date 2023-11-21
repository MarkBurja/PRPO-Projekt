package si.fri.prpo.skupina7.dtos;

import java.time.Instant;

public class OcenaDto {
    private Integer id;
    private Integer uporabnikId;
    private Integer filmId;
    private Integer ocena;
    private String komentar;
    private Instant casOddaje;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUporabnikId() {
        return uporabnikId;
    }

    public void setUporabnikId(Integer uporabnikId) {
        this.uporabnikId = uporabnikId;
    }

    public Integer getFilmId() {
        return filmId;
    }

    public void setFilmId(Integer filmId) {
        this.filmId = filmId;
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
}
