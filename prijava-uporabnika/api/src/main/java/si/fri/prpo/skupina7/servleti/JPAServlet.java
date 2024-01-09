package si.fri.prpo.skupina7.servleti;

import si.fri.prpo.skupina7.dtos.UporabnikDto;
import si.fri.prpo.skupina7.zrna.PrijavaUporabnikaZrno;

import javax.inject.Inject;
import javax.json.JsonArray;
import javax.json.JsonObject;
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
    private PrijavaUporabnikaZrno prijavaUporabnikaZrno;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {


        String uporabniskoIme = null;
        String geslo = null;

        UporabnikDto uporabnik = prijavaUporabnikaZrno.prijavaUporabnika(uporabniskoIme, geslo);
    }
}
