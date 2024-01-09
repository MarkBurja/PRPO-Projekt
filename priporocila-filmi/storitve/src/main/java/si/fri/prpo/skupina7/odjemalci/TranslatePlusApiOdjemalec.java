package si.fri.prpo.skupina7.odjemalci;

import com.kumuluz.ee.configuration.utils.ConfigurationUtil;
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
import java.util.Optional;
import java.util.UUID;
import java.util.logging.Logger;

@ApplicationScoped
public class TranslatePlusApiOdjemalec {

    private UUID id;
    private Logger log = Logger.getLogger(TranslatePlusApiOdjemalec.class.getName());

    private Client httpClient;
    private String baseUrl;
    private String key;

    @PostConstruct
    private void init() {
        id = java.util.UUID.randomUUID();
        log.info("Inicializacija zrna " + TranslatePlusApiOdjemalec.class.getSimpleName() + " (" + id + ")");

        httpClient = ClientBuilder.newClient();
        baseUrl = ConfigurationUtil.getInstance().get("integration-properties.translate-plus-api.host").get();
        key = ConfigurationUtil.getInstance().get("integration-properties.translate-plus-api.api-key").get();
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
                    .header("X-RapidAPI-Key", key)
                    .header("X-RapidAPI-Host", baseUrl)
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
