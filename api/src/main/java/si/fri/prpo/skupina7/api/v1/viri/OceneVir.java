package si.fri.prpo.skupina7.api.v1.viri;

import com.kumuluz.ee.rest.beans.QueryParameters;
import si.fri.prpo.skupina7.dtos.OcenaDto;
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

    @GET
    public Response pridobiOcene() {
        QueryParameters query = QueryParameters.query(uriInfo.getRequestUri().getQuery()).build();
        Long oceneCount = oceneZrno.pridobiOceneCount(query);
        List<Ocena> ocene = oceneZrno.pridobiOcene(query);

        return Response.status(Response.Status.OK).entity(ocene).header("X-Total-Count", oceneCount).build();
    }

    @GET
    @Path("{id}")
    public Response pridobiOceno(@PathParam("id") int id) {
        Ocena ocena = oceneZrno.pridobiOceno(id);

        if(ocena == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.status(Response.Status.OK).entity(ocena).build();
    }

    @POST
    public Response dodajOceno(OcenaDto ocenaDto) {
        Ocena ocena = upravljanjeOcenZrno.dodajOceno(ocenaDto);

        if(ocena == null) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }

        return Response.status(Response.Status.CREATED).entity(ocena).build();
    }

    @PUT
    @Path("{id}")
    public Response posodobiOceno(@PathParam("id") int id, OcenaDto ocenaDto) {
        Ocena ocena = oceneZrno.pridobiOceno(id);

        if(ocena == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        Ocena noviOcena = upravljanjeOcenZrno.posodobiOceno(id, ocenaDto);

        return Response.status(Response.Status.OK).entity(noviOcena).build();
    }

    @DELETE
    @Path("{id}")
    public Response odstraniOceno(@PathParam("id") int id) {

        if(oceneZrno.odstraniOceno(id)) {
            return Response.status(Response.Status.OK).build();
        }

        return Response.status(Response.Status.NOT_FOUND).build();
    }
}
