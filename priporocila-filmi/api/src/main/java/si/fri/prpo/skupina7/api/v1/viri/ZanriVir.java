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
import si.fri.prpo.skupina7.dtos.ZanrDto;
import si.fri.prpo.skupina7.entitete.Film;
import si.fri.prpo.skupina7.entitete.Uporabnik;
import si.fri.prpo.skupina7.entitete.Zanr;
import si.fri.prpo.skupina7.zrna.ZanriZrno;
import si.fri.prpo.skupina7.zrna.UpravljanjeZanrovZrno;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.List;

@Path("zanri")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@CrossOrigin(supportedMethods = "GET, POST, HEAD, DELETE, PUT, PATCH, OPTIONS")
@ApplicationScoped
public class ZanriVir {

    @Context
    protected UriInfo uriInfo;

    @Inject
    private ZanriZrno zanriZrno;

    @Inject
    private UpravljanjeZanrovZrno upravljanjeZanrovZrno;

    @Operation(description = "Vrne seznam vseh žanrov.", summary = "Vrni seznam žanrov")
    @APIResponses({
            @APIResponse(responseCode = "200",
                    description = "Seznam žanrov uspešno pridobljen",
                    content = @Content(schema = @Schema(implementation = Zanr.class, type = SchemaType.ARRAY)),
                    headers =  {@Header(name = "X-Total-Count", description = "Število vrnjenih žanrov")}
            )
    })
    @GET
    public Response pridobiZanre() {
        QueryParameters query = QueryParameters.query(uriInfo.getRequestUri().getQuery()).build();
        Long zanriCount = zanriZrno.pridobiZanreCount(query);
        List<Zanr> zanri = zanriZrno.pridobiZanre(query);

        return Response.status(Response.Status.OK).entity(zanri).header("X-Total-Count", zanriCount).build();
    }

    @Operation(description = "Vrne specifičen žanr.", summary = "Vrni Žanr")
    @APIResponses({
            @APIResponse(responseCode = "200", description = "Žanr uspešno pridobljen",
                    content = @Content(schema = @Schema(implementation = Zanr.class))
            ),
            @APIResponse(responseCode = "404", description = "Žanr ne obstaja.")
    })
    @GET
    @Path("{id}")
    public Response pridobiZanr(@PathParam("id") int id) {
        Zanr zanr = zanriZrno.pridobiZanr(id);

        if(zanr == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.status(Response.Status.OK).entity(zanr).build();
    }

    @Operation(description = "Doda nov žanr.", summary = "Dodaj žanr")
    @APIResponses({
            @APIResponse(responseCode = "201", description = "Žanr uspešno dodan.",
                    content = @Content(schema = @Schema(implementation = Zanr.class))),
            @APIResponse(responseCode = "500", description = "Napaka na strežniku.")
    })
    @POST
    public Response dodajZanr(@RequestBody(
            description = "DTO objekt za žanr", required = true,
            content = @Content(schema = @Schema(implementation = ZanrDto.class))) ZanrDto zanrDto) {

        Zanr zanr = upravljanjeZanrovZrno.dodajZanr(zanrDto);

        if(zanr == null) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }

        return Response.status(Response.Status.CREATED).entity(zanr).build();
    }

    @Operation(description = "Posodobi obstoječ žanr.", summary = "Posodobi žanr")
    @APIResponses({
            @APIResponse(responseCode = "200", description = "Žanr uspešno posodobljen.",
                    content = @Content(schema = @Schema(implementation = Zanr.class))),
            @APIResponse(responseCode = "404", description = "Žanr ne obstaja."),
            @APIResponse(responseCode = "500", description = "Napaka na strežniku.")
    })
    @PUT
    @Path("{id}")
    public Response posodobiZanr(@PathParam("id") int id, @RequestBody(
            description = "DTO objekt za žanr", required = true,
            content = @Content(schema = @Schema(implementation = ZanrDto.class))) ZanrDto zanrDto) {

        Zanr zanr = zanriZrno.pridobiZanr(id);

        if(zanr == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        Zanr noviZanr = upravljanjeZanrovZrno.posodobiZanr(id, zanrDto);

        return Response.status(Response.Status.OK).entity(noviZanr).build();
    }

    @Operation(description = "Izbriše specifičen žanr.", summary = "Izbriši žanr")
    @APIResponses({
            @APIResponse(responseCode = "200", description = "Žanr uspešno izbrisan."),
            @APIResponse(responseCode = "404", description = "Žanr ne obstaja.")
    })
    @DELETE
    @Path("{id}")
    public Response odstraniZanr(@PathParam("id") int id) {

        if(zanriZrno.odstraniZanr(id)) {
            return Response.status(Response.Status.OK).build();
        }

        return Response.status(Response.Status.NOT_FOUND).build();
    }
}
