package si.fri.prpo.skupina7.api.v1.viri;

import com.kumuluz.ee.rest.beans.QueryParameters;
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
@ApplicationScoped
public class UporabnikiVir {

    @Context
    protected UriInfo uriInfo;

    @Inject
    private UporabnikiZrno uporabnikiZrno;

    @Inject
    private UpravljanjeUporabnikovZrno upravljanjeUporabnikovZrno;

    @GET
    public Response pridobiUporabnike() {
        QueryParameters query = QueryParameters.query(uriInfo.getRequestUri().getQuery()).build();
        Long uporabnikiCount = uporabnikiZrno.pridobiUporabnikeCount(query);
        List<Uporabnik> uporabniki = uporabnikiZrno.pridobiUporabnike(query);

        return Response.status(Response.Status.OK).entity(uporabniki).header("X-Total-Count", uporabnikiCount).build();
    }

    @GET
    @Path("{id}")
    public Response pridobiUporabnika(@PathParam("id") int id) {
        Uporabnik uporabnik = uporabnikiZrno.pridobiUporabnika(id);

        if(uporabnik == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.status(Response.Status.OK).entity(uporabnik).build();
    }

    @POST
    public Response dodajUporabnika(UporabnikDto uporabnikDto) {
        Uporabnik uporabnik = upravljanjeUporabnikovZrno.dodajUporabnika(uporabnikDto);

        if(uporabnik == null) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }

        return Response.status(Response.Status.CREATED).entity(uporabnik).build();
    }

    @PUT
    @Path("{id}")
    public Response posodobiUporabnika(@PathParam("id") int id, UporabnikDto uporabnikDto) {
        Uporabnik uporabnik = uporabnikiZrno.pridobiUporabnika(id);

        if(uporabnik == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        Uporabnik noviUporabnik = upravljanjeUporabnikovZrno.posodobiUporabnika(id, uporabnikDto);

        return Response.status(Response.Status.OK).entity(noviUporabnik).build();
    }

    @DELETE
    @Path("{id}")
    public Response odstraniUporabnika(@PathParam("id") int id) {

        if(uporabnikiZrno.odstraniUporabnika(id)) {
            return Response.status(Response.Status.OK).build();
        }

        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @PATCH
    @Path("{id}/spremeniGeslo")
    public Response spremeniGeslo(@PathParam("id") int id, UporabnikDto uporabnikDto) {
        Uporabnik uporabnik = uporabnikiZrno.pridobiUporabnika(id);

        if(uporabnik == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        Uporabnik noviUporabnik = upravljanjeUporabnikovZrno.spremeniGeslo(id, uporabnikDto);

        return Response.status(Response.Status.OK).entity(noviUporabnik).build();
    }
}