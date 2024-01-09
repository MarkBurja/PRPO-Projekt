package si.fri.prpo.skupina7.api.v1.viri;

import com.kumuluz.ee.cors.annotations.CrossOrigin;
import com.kumuluz.ee.rest.beans.QueryParameters;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.headers.Header;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import si.fri.prpo.skupina7.dtos.FilmDto;
import si.fri.prpo.skupina7.entitete.Film;
import si.fri.prpo.skupina7.odjemalci.TranslatePlusApiOdjemalec;
import si.fri.prpo.skupina7.zrna.FilmiZrno;
import si.fri.prpo.skupina7.zrna.UpravljanjeFilmovZrno;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.List;

@Path("filmi")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@CrossOrigin(supportedMethods = "GET, POST, HEAD, DELETE, PUT, PATCH, OPTIONS")
@ApplicationScoped
public class FilmiVir {

    @Context
    protected UriInfo uriInfo;

    @Inject
    private FilmiZrno filmiZrno;

    @Inject
    private UpravljanjeFilmovZrno upravljanjeFilmovZrno;

    @Inject
    private TranslatePlusApiOdjemalec translatePlusApiOdjemalec;

    @Operation(description = "Vrne seznam vseh filmov.", summary = "Vrni seznam filmov")
    @APIResponses({
            @APIResponse(responseCode = "200",
                    description = "Seznam filmov uspešno pridobljen",
                    content = @Content(schema = @Schema(implementation = Film.class, type = SchemaType.ARRAY)),
                    headers =  {@Header(name = "X-Total-Count", description = "Število vrnjenih filmov")}
            )
    })
    @GET
    public Response pridobiFilme() {
        QueryParameters query = QueryParameters.query(uriInfo.getRequestUri().getQuery()).build();
        Long filmiCount = filmiZrno.pridobiFilmeCount(query);
        List<Film> filmi = filmiZrno.pridobiFilme(query);

        return Response.status(Response.Status.OK).entity(filmi).header("X-Total-Count", filmiCount).build();
    }

    @Operation(description = "Vrne specifičen film.", summary = "Vrni film")
    @APIResponses({
            @APIResponse(responseCode = "200", description = "Film uspešno pridobljen",
                    content = @Content(schema = @Schema(implementation = Film.class))
            ),
            @APIResponse(responseCode = "404", description = "Film ne obstaja.")
    })
    @GET
    @Path("{id}")
    public Response pridobiFilm(@PathParam("id") int id) {
        Film film = filmiZrno.pridobiFilm(id);

        if (film == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.status(Response.Status.OK).entity(film).build();
    }

    @Operation(description = "Doda nov film.", summary = "Dodaj film")
    @APIResponses({
            @APIResponse(responseCode = "201", description = "Film uspešno dodan.",
                    content = @Content(schema = @Schema(implementation = Film.class))),
            @APIResponse(responseCode = "500", description = "Napaka na strežniku.")
    })
    @POST
    public Response dodajFilm(@RequestBody(
            description = "DTO objekt za film", required = true,
            content = @Content(schema = @Schema(implementation = FilmDto.class))) FilmDto filmDto) {

        Film film = upravljanjeFilmovZrno.dodajFilm(filmDto);

        if(film == null) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }

        return Response.status(Response.Status.CREATED).entity(film).build();
    }

    @Operation(description = "Posodobi obstoječ film.", summary = "Posodobi film")
    @APIResponses({
            @APIResponse(responseCode = "200", description = "Film uspešno posodobljen.",
                    content = @Content(schema = @Schema(implementation = Film.class))),
            @APIResponse(responseCode = "404", description = "Film ne obstaja."),
            @APIResponse(responseCode = "500", description = "Napaka na strežniku.")
    })
    @PUT
    @Path("{id}")
    public Response posodobiFilm(@PathParam("id") int id, @RequestBody(
            description = "DTO objekt za film", required = true,
            content = @Content(schema = @Schema(implementation = FilmDto.class))) FilmDto filmDto) {

        Film film = filmiZrno.pridobiFilm(id);

        if(film == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        Film noviFilm = upravljanjeFilmovZrno.posodobiFilm(id, filmDto);

        return Response.status(Response.Status.OK).entity(noviFilm).build();
    }

    @Operation(description = "Izbriše specifičen film.", summary = "Izbriši film")
    @APIResponses({
            @APIResponse(responseCode = "200", description = "Film uspešno izbrisan."),
            @APIResponse(responseCode = "404", description = "Film ne obstaja.")
    })
    @DELETE
    @Path("{id}")
    public Response odstraniFilm(@PathParam("id") int id) {

        if(filmiZrno.odstraniFilm(id)) {
            return Response.status(Response.Status.OK).build();
        }

        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @Operation(description = "Vrne specifičen film z naslovom, prevedenim v slovenščino.", summary = "Prevedi naslov filma")
    @APIResponses({
            @APIResponse(responseCode = "200", description = "Prevod uspešno pridobljen",
                    content = @Content(schema = @Schema(implementation = Film.class))),
            @APIResponse(responseCode = "404", description = "Film ne obstaja."),
            @APIResponse(responseCode = "500", description = "Napaka na strežniku.")
    })
    @GET
    @Path("{id}/prevediNaslov")
    public Response prevediNaslov(@PathParam("id") int id) {
        Film film = filmiZrno.pridobiFilm(id);

        if(film == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        String prevod = translatePlusApiOdjemalec.prevediNaslov(film.getNaslov());
        film.setNaslov(prevod);

        return Response.status(Response.Status.OK).entity(film).build();
    }
}
