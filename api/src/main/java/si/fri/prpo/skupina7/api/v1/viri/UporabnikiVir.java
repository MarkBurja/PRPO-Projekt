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
import si.fri.prpo.skupina7.dtos.UporabnikDto;
import si.fri.prpo.skupina7.entitete.Uporabnik;
import si.fri.prpo.skupina7.zrna.UporabnikiZrno;
import si.fri.prpo.skupina7.zrna.UpravljanjeUporabnikovZrno;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.List;

@Path("uporabniki")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@CrossOrigin(supportedMethods = "GET, POST, HEAD, DELETE, PUT, PATCH, OPTIONS")
@ApplicationScoped
public class UporabnikiVir {

    @Context
    protected UriInfo uriInfo;

    @Inject
    private UporabnikiZrno uporabnikiZrno;

    @Inject
    private UpravljanjeUporabnikovZrno upravljanjeUporabnikovZrno;

    @Operation(description = "Vrne seznam vseh uporabnikov.", summary = "Vrni seznam uporabnikov")
    @APIResponses({
            @APIResponse(responseCode = "200",
                    description = "Seznam uporabnikov uspešno pridobljen",
                    content = @Content(schema = @Schema(implementation = Uporabnik.class, type = SchemaType.ARRAY)),
                    headers =  {@Header(name = "X-Total-Count", description = "Število vrnjenih uporabnikov")}
            )
    })
    @GET
    public Response pridobiUporabnike() {
        QueryParameters query = QueryParameters.query(uriInfo.getRequestUri().getQuery()).build();
        Long uporabnikiCount = uporabnikiZrno.pridobiUporabnikeCount(query);
        List<Uporabnik> uporabniki = uporabnikiZrno.pridobiUporabnike(query);

        return Response.status(Response.Status.OK).entity(uporabniki).header("X-Total-Count", uporabnikiCount).build();
    }

    @Operation(description = "Vrne specifičnega uporabnika.", summary = "Vrni uporabnika")
    @APIResponses({
            @APIResponse(responseCode = "200", description = "Uporabnik uspešno pridobljen",
                    content = @Content(schema = @Schema(implementation = Uporabnik.class))
            ),
            @APIResponse(responseCode = "404", description = "Uporabnik ne obstaja.")
    })
    @GET
    @Path("{id}")
    public Response pridobiUporabnika(@PathParam("id") int id) {
        Uporabnik uporabnik = uporabnikiZrno.pridobiUporabnika(id);

        if(uporabnik == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.status(Response.Status.OK).entity(uporabnik).build();
    }

    @Operation(description = "Doda novega uporabnika.", summary = "Dodaj uporabnika")
    @APIResponses({
            @APIResponse(responseCode = "201", description = "Uporabnik uspešno dodan.",
                    content = @Content(schema = @Schema(implementation = Uporabnik.class))),
            @APIResponse(responseCode = "405", description = "Validacijska napaka."),
            @APIResponse(responseCode = "500", description = "Napaka na strežniku.")
    })
    @POST
    public Response dodajUporabnika(@RequestBody(
            description = "DTO objekt za uporabnika", required = true,
            content = @Content(schema = @Schema(implementation = UporabnikDto.class))) UporabnikDto uporabnikDto) {

        Uporabnik uporabnik = upravljanjeUporabnikovZrno.dodajUporabnika(uporabnikDto);

        if(uporabnik == null) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }

        return Response.status(Response.Status.CREATED).entity(uporabnik).build();
    }

    @Operation(description = "Posodobi obstoječega uporabnika.", summary = "Posodobi uporabnika")
    @APIResponses({
            @APIResponse(responseCode = "200", description = "Uporabnik uspešno posodobljen.",
                    content = @Content(schema = @Schema(implementation = Uporabnik.class))),
            @APIResponse(responseCode = "404", description = "Uporabnik ne obstaja."),
            @APIResponse(responseCode = "500", description = "Napaka na strežniku.")
    })
    @PUT
    @Path("{id}")
    public Response posodobiUporabnika(@PathParam("id") int id, @RequestBody(
            description = "DTO objekt za uporabnika", required = true,
            content = @Content(schema = @Schema(implementation = UporabnikDto.class))) UporabnikDto uporabnikDto) {

        Uporabnik uporabnik = uporabnikiZrno.pridobiUporabnika(id);

        if(uporabnik == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        Uporabnik noviUporabnik = upravljanjeUporabnikovZrno.posodobiUporabnika(id, uporabnikDto);

        return Response.status(Response.Status.OK).entity(noviUporabnik).build();
    }

    @Operation(description = "Izbriše specifičnega uporabnika.", summary = "Izbriši uporabnika")
    @APIResponses({
            @APIResponse(responseCode = "200", description = "Uporabnik uspešno izbrisan."),
            @APIResponse(responseCode = "404", description = "Uporabnik ne obstaja.")
    })
    @DELETE
    @Path("{id}")
    public Response odstraniUporabnika(@PathParam("id") int id) {

        if(uporabnikiZrno.odstraniUporabnika(id)) {
            return Response.status(Response.Status.OK).build();
        }

        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @Operation(description = "Spremeni geslo specifičnega uporabnika.", summary = "Spremeni geslo uporabnika")
    @APIResponses({
            @APIResponse(responseCode = "200", description = "Geslo uspešno spremenjeno.",
                    content = @Content(schema = @Schema(implementation = Uporabnik.class))),
            @APIResponse(responseCode = "404", description = "Uporabnik ne obstaja."),
            @APIResponse(responseCode = "500", description = "Napaka na strežniku.")
    })
    @PATCH
    @Path("{id}/spremeniGeslo")
    public Response spremeniGeslo(@PathParam("id") int id, @RequestBody(
            description = "DTO objekt za uporabnika", required = true,
            content = @Content(schema = @Schema(implementation = UporabnikDto.class))) UporabnikDto uporabnikDto) {

        Uporabnik uporabnik = uporabnikiZrno.pridobiUporabnika(id);

        if(uporabnik == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        Uporabnik noviUporabnik = upravljanjeUporabnikovZrno.spremeniGeslo(id, uporabnikDto);

        return Response.status(Response.Status.OK).entity(noviUporabnik).build();
    }
}