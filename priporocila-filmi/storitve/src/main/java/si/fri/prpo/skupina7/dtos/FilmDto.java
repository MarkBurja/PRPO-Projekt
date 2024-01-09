package si.fri.prpo.skupina7.dtos;

public class FilmDto {
    private Integer id;
    private String naslov;
    private String opis;
    private Integer leto;
    private Integer rating;
    private Integer zanrId;

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

    public Integer getZanrId() {
        return zanrId;
    }

    public void setZanrId(Integer zanrId) {
        this.zanrId = zanrId;
    }
}
