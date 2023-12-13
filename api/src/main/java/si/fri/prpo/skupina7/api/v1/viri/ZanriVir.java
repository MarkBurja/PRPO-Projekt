package si.fri.prpo.skupina7.api.v1.viri;

import si.fri.prpo.skupina7.dtos.ZanrDto;
import si.fri.prpo.skupina7.entitete.Zanr;
import si.fri.prpo.skupina7.zrna.ZanriZrno;
import si.fri.prpo.skupina7.zrna.UpravljanjeZanrovZrno;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("zanri")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@ApplicationScoped
public class ZanriVir {

    @Inject
    private ZanriZrno zanriZrno;

    @Inject
    private UpravljanjeZanrovZrno upravljanjeZanrovZrno;

    @GET
    public Response pridobiZanre() {
        List<Zanr> zanri = zanriZrno.pridobiZanre();

        return Response.status(Response.Status.OK).entity(zanri).build();
    }

    @GET
    @Path("{id}")
    public Response pridobiZanr(@PathParam("id") int id) {
        Zanr zanr = zanriZrno.pridobiZanr(id);

        if(zanr == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.status(Response.Status.OK).entity(zanr).build();
    }

    @POST
    public Response dodajZanr(ZanrDto zanrDto) {
        Zanr zanr = upravljanjeZanrovZrno.dodajZanr(zanrDto);

        if(zanr == null) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }

        return Response.status(Response.Status.CREATED).entity(zanr).build();
    }

    @PUT
    @Path("{id}")
    public Response posodobiZanr(@PathParam("id") int id, ZanrDto zanrDto) {
        Zanr zanr = zanriZrno.pridobiZanr(id);

        if(zanr == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        Zanr noviZanr = upravljanjeZanrovZrno.posodobiZanr(id, zanrDto);

        return Response.status(Response.Status.OK).entity(noviZanr).build();
    }

    @DELETE
    @Path("{id}")
    public Response odstraniZanr(@PathParam("id") int id) {

        if(zanriZrno.odstraniZanr(id)) {
            return Response.status(Response.Status.OK).build();
        }

        return Response.status(Response.Status.NOT_FOUND).build();
    }
}
