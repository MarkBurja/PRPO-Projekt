package si.fri.prpo.skupina7.api.v1.viri;

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
import si.fri.prpo.skupina7.dtos.OcenaDto;
import si.fri.prpo.skupina7.entitete.Film;
import si.fri.prpo.skupina7.entitete.Ocena;
import si.fri.prpo.skupina7.entitete.Uporabnik;
import si.fri.prpo.skupina7.zrna.OceneZrno;
import si.fri.prpo.skupina7.zrna.UpravljanjeOcenZrno;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.List;

@Path("ocene")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@ApplicationScoped
public class OceneVir {

    @Context
    protected UriInfo uriInfo;

    @Inject
    private OceneZrno oceneZrno;

    @Inject
    private UpravljanjeOcenZrno upravljanjeOcenZrno;

    @Operation(description = "Vrne seznam vseh ocen.", summary = "Vrni seznam ocen")
    @APIResponses({
            @APIResponse(responseCode = "200",
                    description = "Seznam ocen uspešno pridobljen",
                    content = @Content(schema = @Schema(implementation = Ocena.class, type = SchemaType.ARRAY)),
                    headers =  {@Header(name = "X-Total-Count", description = "Število vrnjenih ocen")}
            )
    })
    @GET
    public Response pridobiOcene() {
        QueryParameters query = QueryParameters.query(uriInfo.getRequestUri().getQuery()).build();
        Long oceneCount = oceneZrno.pridobiOceneCount(query);
        List<Ocena> ocene = oceneZrno.pridobiOcene(query);

        return Response.status(Response.Status.OK).entity(ocene).header("X-Total-Count", oceneCount).build();
    }

    @Operation(description = "Vrne specifično oceno.", summary = "Vrni oceno")
    @APIResponses({
            @APIResponse(responseCode = "200", description = "Ocena uspešno pridobljen",
                    content = @Content(schema = @Schema(implementation = Ocena.class))
            ),
            @APIResponse(responseCode = "404", description = "Ocena ne obstaja.")
    })
    @GET
    @Path("{id}")
    public Response pridobiOceno(@PathParam("id") int id) {
        Ocena ocena = oceneZrno.pridobiOceno(id);

        if(ocena == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.status(Response.Status.OK).entity(ocena).build();
    }

    @Operation(description = "Doda novo oceno.", summary = "Dodaj oceno")
    @APIResponses({
            @APIResponse(responseCode = "201", description = "Ocena uspešno dodana.",
                    content = @Content(schema = @Schema(implementation = Ocena.class))),
            @APIResponse(responseCode = "500", description = "Napaka na strežniku.")
    })
    @POST
    public Response dodajOceno(@RequestBody(
            description = "DTO objekt za oceno", required = true,
            content = @Content(schema = @Schema(implementation = OcenaDto.class))) OcenaDto ocenaDto) {

        Ocena ocena = upravljanjeOcenZrno.dodajOceno(ocenaDto);

        if(ocena == null) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }

        return Response.status(Response.Status.CREATED).entity(ocena).build();
    }

    @Operation(description = "Posodobi obstoječo oceno.", summary = "Posodobi oceno")
    @APIResponses({
            @APIResponse(responseCode = "200", description = "Ocena uspešno posodobljena.",
                    content = @Content(schema = @Schema(implementation = Ocena.class))),
            @APIResponse(responseCode = "404", description = "Ocena ne obstaja."),
            @APIResponse(responseCode = "500", description = "Napaka na strežniku.")
    })
    @PUT
    @Path("{id}")
    public Response posodobiOceno(@PathParam("id") int id, @RequestBody(
            description = "DTO objekt za oceno", required = true,
            content = @Content(schema = @Schema(implementation = OcenaDto.class))) OcenaDto ocenaDto) {

        Ocena ocena = oceneZrno.pridobiOceno(id);

        if(ocena == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        Ocena noviOcena = upravljanjeOcenZrno.posodobiOceno(id, ocenaDto);

        return Response.status(Response.Status.OK).entity(noviOcena).build();
    }

    @Operation(description = "Izbriše specifično oceno.", summary = "Izbriši oceno")
    @APIResponses({
            @APIResponse(responseCode = "200", description = "Ocena uspešno izbrisana."),
            @APIResponse(responseCode = "404", description = "Ocena ne obstaja.")
    })
    @DELETE
    @Path("{id}")
    public Response odstraniOceno(@PathParam("id") int id) {

        if(oceneZrno.odstraniOceno(id)) {
            return Response.status(Response.Status.OK).build();
        }

        return Response.status(Response.Status.NOT_FOUND).build();
    }
}
