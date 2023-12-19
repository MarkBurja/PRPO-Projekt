package si.fri.prpo.skupina7.odjemalci;

import si.fri.prpo.skupina7.zrna.FilmiZrno;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.json.JsonObject;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.ProcessingException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import java.util.UUID;
import java.util.logging.Logger;

@ApplicationScoped
public class TranslatePlusApiOdjemalec {

    private UUID id;
    private Logger log = Logger.getLogger(TranslatePlusApiOdjemalec.class.getName());

    private Client httpClient;
    private String baseUrl;

    @PostConstruct
    private void init() {
        id = java.util.UUID.randomUUID();
        log.info("Inicializacija zrna " + TranslatePlusApiOdjemalec.class.getSimpleName() + " (" + id + ")");

        httpClient = ClientBuilder.newClient();
        baseUrl = "text-translator2.p.rapidapi.com";
    }

    @PreDestroy
    private void destroy() {
        log.info("Deinicializacija zrna " + FilmiZrno.class.getSimpleName() + " (" + id + ")");
    }

    public String prevediNaslov(String naslov) {

        JsonObject response = null;

        String body = "{" +
                "\"text\": \"" + naslov + "\"," +
                "\"source\": \"en\"," +
                "\"target\": \"sl\"" +
                "}";

        try {
            response = httpClient
                    .target("https://" + baseUrl + "/translate")
                    .request(MediaType.APPLICATION_JSON)
                    .header("content-type", "application/json")
                    .header("X-RapidAPI-Key", "f21e48fea3msh6904a6d85b24218p14e377jsn8efe83074f6a")
                    .header("X-RapidAPI-Host", "translate-plus.p.rapidapi.com")
                    .post(Entity.json(body))
                    .readEntity(JsonObject.class);
        } catch (WebApplicationException | ProcessingException e) {
            log.severe(e.getMessage());
            throw new InternalServerErrorException(e);
        }

        String prevod = response.getJsonObject("translations").getString("translation");

        return prevod;
    }
}
