package si.fri.prpo.skupina7.api.v1.viri;

import com.kumuluz.ee.cors.annotations.CrossOrigin;
import si.fri.prpo.skupina7.dtos.UporabnikDto;;
import si.fri.prpo.skupina7.zrna.PrijavaUporabnikaZrno;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.logging.Logger;

@Path("prijavaUporabnika")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@CrossOrigin(supportedMethods = "GET, POST, HEAD, DELETE, PUT, PATCH, OPTIONS")
@ApplicationScoped
public class PrijavaUporabnikaVir {

    @Context
    protected UriInfo uriInfo;

    @Inject
    private PrijavaUporabnikaZrno prijavaUporabnikaZrno;

    private Logger log = Logger.getLogger(PrijavaUporabnikaVir.class.getName());

    @POST
    public Response prijavaUporabnika(UporabnikDto uporabnikDto) {

        String uporabniskoIme = uporabnikDto.getUporabniskoIme();
        String geslo = uporabnikDto.getGeslo();

        UporabnikDto uporabnik = prijavaUporabnikaZrno.prijavaUporabnika(uporabniskoIme, geslo);

        if(uporabnik == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.status(Response.Status.OK).entity(uporabnik).build();
    }
}
