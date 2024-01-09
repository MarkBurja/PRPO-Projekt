package si.fri.prpo.skupina7.zrna;

import com.kumuluz.ee.configuration.utils.ConfigurationUtil;
import si.fri.prpo.skupina7.dtos.UporabnikDto;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.json.JsonArray;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.ProcessingException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.json.JsonObject;
import java.util.UUID;
import java.util.logging.Logger;

@ApplicationScoped
public class PrijavaUporabnikaZrno {

    private UUID id;
    private Logger log = Logger.getLogger(PrijavaUporabnikaZrno.class.getName());

    private Client httpClient;
    private String baseUrl;

    @PostConstruct
    private void init() {
        id = java.util.UUID.randomUUID();
        log.info("Inicializacija zrna " + PrijavaUporabnikaZrno.class.getSimpleName() + " (" + id + ")");

        httpClient = ClientBuilder.newClient();
        baseUrl = ConfigurationUtil.getInstance().get("integration-properties.priporocila-filmi-api.host").get();
        log.info(baseUrl);
    }

    @PreDestroy
    private void destroy() {
        log.info("Deinicializacija zrna " + PrijavaUporabnikaZrno.class.getSimpleName() + " (" + id + ")");
    }

    public UporabnikDto prijavaUporabnika(String uporabniskoIme, String geslo) {

        JsonArray response = null;

        try {
            response = httpClient
                    .target(baseUrl + "/uporabniki")
                    .request()
                    .get()
                    .readEntity(JsonArray.class);
        } catch (WebApplicationException | ProcessingException e) {
            log.severe(e.getMessage());
            throw new InternalServerErrorException(e);
        }

        for(int i = 0; i < response.size(); i++) {
            JsonObject u = response.getJsonObject(i);
            String ui = u.getString("uporabniskoIme");
            String g = u.getString("geslo");
            if(uporabniskoIme != null && uporabniskoIme.equals(ui) && geslo != null && geslo.equals(g)){
                UporabnikDto uporabnik = new UporabnikDto();
                uporabnik.setId(u.getInt("id"));
                uporabnik.setUporabniskoIme(ui);
                uporabnik.setGeslo(g);
                uporabnik.setEmail(u.getString("email"));
                uporabnik.setIme(u.getString("ime"));
                uporabnik.setPriimek(u.getString("priimek"));
                uporabnik.setStarost(u.getInt("starost"));
                uporabnik.setSpol(u.getString("spol"));

                return uporabnik;
            };
        }

        return null;
    }
}
