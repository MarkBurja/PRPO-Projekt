package si.fri.prpo.skupina7.api.v1;

import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition;
import org.eclipse.microprofile.openapi.annotations.info.Contact;
import org.eclipse.microprofile.openapi.annotations.info.Info;
import org.eclipse.microprofile.openapi.annotations.info.License;
import org.eclipse.microprofile.openapi.annotations.servers.Server;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

@OpenAPIDefinition(
        info = @Info(title = "Priporočila filmov API", version = "v1",
                description = "API za storitev Priporočila filmov",
                contact = @Contact(email = "mb4400@student.uni-lj.si"),
                license = @License(name = "Mark Burja")), servers = @Server(url = "http://localhost:8080"))
@ApplicationPath("v1")
public class PriporocilaFilmiApplication extends Application {
}
