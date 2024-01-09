package si.fri.prpo.skupina7.interceptorji;

import si.fri.prpo.skupina7.anotacije.BeleziKlice;

import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

@Interceptor
@BeleziKlice
public class BelezenjeKlicevInterceptor {

    private Logger log = Logger.getLogger(BelezenjeKlicevInterceptor.class.getName());

    private HashMap<String, Integer> methodCount = new HashMap<String, Integer>();

    @AroundInvoke
    public Object beleziKlic(InvocationContext context) throws Exception {

        String name = context.getMethod().getName();
        int counter = 1;

        if(methodCount.containsKey(name)) {
            counter = methodCount.get(name) + 1;
        }

        methodCount.put(name, counter);

        log.info(name + ": " + counter);

        return context.proceed();
    }
}
