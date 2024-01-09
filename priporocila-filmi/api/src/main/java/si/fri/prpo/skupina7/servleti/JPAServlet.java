package si.fri.prpo.skupina7.servleti;

import si.fri.prpo.skupina7.dtos.OcenaDto;
import si.fri.prpo.skupina7.entitete.Film;
import si.fri.prpo.skupina7.entitete.Ocena;
import si.fri.prpo.skupina7.zrna.FilmiZrno;
import si.fri.prpo.skupina7.zrna.OceneZrno;
import si.fri.prpo.skupina7.zrna.UpravljanjeOcenZrno;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/servlet")
public class JPAServlet extends HttpServlet {

    @Inject
    private FilmiZrno filmiZrno;

    @Inject
    private UpravljanjeOcenZrno upravljanjeOcenZrno;

    @Inject
    private OceneZrno oceneZrno;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        resp.getWriter().println("FILMI:");

        List<Film> filmi = filmiZrno.pridobiFilme();

        for (Film film : filmi) {
            resp.getWriter().println(film.toString());
        }

        resp.getWriter().println();
        resp.getWriter().println("OCENE:");

        OcenaDto novaOcena = new OcenaDto();
        novaOcena.setFilmId(1);
        novaOcena.setUporabnikId(2);
        novaOcena.setOcena(9);
        novaOcena.setKomentar("Dober film.");
        upravljanjeOcenZrno.dodajOceno(novaOcena);

        novaOcena.setFilmId(2);
        novaOcena.setOcena(8);
        upravljanjeOcenZrno.dodajOceno(novaOcena);

        List <Ocena> ocene = oceneZrno.pridobiOcene();

        for(Ocena ocena : ocene) {
            resp.getWriter().println(ocena.toString());
        }



    }
}
