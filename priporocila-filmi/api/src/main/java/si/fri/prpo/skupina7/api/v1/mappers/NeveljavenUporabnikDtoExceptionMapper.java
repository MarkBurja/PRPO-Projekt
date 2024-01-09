package si.fri.prpo.skupina7.api.v1.mappers;

import si.fri.prpo.skupina7.izjeme.NeveljavenUporabnikDtoIzjema;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class NeveljavenUporabnikDtoExceptionMapper implements ExceptionMapper<NeveljavenUporabnikDtoIzjema> {

    @Override
    public Response toResponse(NeveljavenUporabnikDtoIzjema exception) {
        return Response.status(Response.Status.BAD_REQUEST).entity("{\"napaka\":\"" + exception.getMessage() + "\"}").build();
    }
}
