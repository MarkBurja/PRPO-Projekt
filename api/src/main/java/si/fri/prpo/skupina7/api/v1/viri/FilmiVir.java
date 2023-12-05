package si.fri.prpo.skupina7.api.v1.viri;

import si.fri.prpo.skupina7.dtos.FilmDto;
import si.fri.prpo.skupina7.entitete.Film;
import si.fri.prpo.skupina7.zrna.FilmiZrno;
import si.fri.prpo.skupina7.zrna.UpravljanjeFilmovZrno;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("filmi")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@ApplicationScoped
public class FilmiVir {

    @Inject
    private FilmiZrno filmiZrno;

    @Inject
    private UpravljanjeFilmovZrno upravljanjeFilmovZrno;

    @GET
    public Response pridobiFilme() {
        List<Film> filmi = filmiZrno.pridobiFilme();

        return Response.status(Response.Status.OK).entity(filmi).build();
    }

    @GET
    @Path("{id}")
    public Response pridobiFilm(@PathParam("id") int id) {
        Film film = filmiZrno.pridobiFilm(id);

        if(film == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.status(Response.Status.OK).entity(film).build();
    }

    @POST
    public Response dodajFilm(FilmDto filmDto) {
        Film film = upravljanjeFilmovZrno.dodajFilm(filmDto);

        if(film == null) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }

        return Response.status(Response.Status.CREATED).entity(film).build();
    }

    @PUT
    @Path("{id}")
    public Response posodobiFilm(@PathParam("id") int id, FilmDto filmDto) {
        Film film = filmiZrno.pridobiFilm(id);

        if(film == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        Film noviFilm = upravljanjeFilmovZrno.posodobiFilm(id, filmDto);

        return Response.status(Response.Status.OK).entity(noviFilm).build();
    }

    @DELETE
    @Path("{id}")
    public Response odstraniFilm(@PathParam("id") int id) {

        if(filmiZrno.odstraniFilm(id)) {
            return Response.status(Response.Status.OK).build();
        }

        return Response.status(Response.Status.NOT_FOUND).build();
    }
}
