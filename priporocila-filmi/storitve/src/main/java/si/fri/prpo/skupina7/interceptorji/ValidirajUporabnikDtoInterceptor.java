package si.fri.prpo.skupina7.interceptorji;

import si.fri.prpo.skupina7.anotacije.ValidirajUporabnikDto;
import si.fri.prpo.skupina7.dtos.UporabnikDto;
import si.fri.prpo.skupina7.izjeme.NeveljavenUporabnikDtoIzjema;

import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import java.util.logging.Logger;

@Interceptor
@ValidirajUporabnikDto
public class ValidirajUporabnikDtoInterceptor {
    private Logger log = Logger.getLogger(ValidirajUporabnikDtoInterceptor.class.getName());

    @AroundInvoke
    public Object validirajUporabnikDto(InvocationContext context) throws Exception {
        if(context.getParameters().length == 1 && context.getParameters()[0] instanceof UporabnikDto) {
            UporabnikDto uporabnik = (UporabnikDto) context.getParameters()[0];

            if(uporabnik.getUporabniskoIme() == null || uporabnik.getUporabniskoIme().isEmpty() ||
                    uporabnik.getEmail() == null || uporabnik.getEmail().isEmpty()) {

                String msg = "Podan uporabnik ne vsebuje vseh obveznih podatkov";
                log.severe(msg);
                throw new NeveljavenUporabnikDtoIzjema(msg);
            }
        }

        return context.proceed();
    }
}
