package Raani.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI raaniOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Raani API")
                        .description("WhatsApp commerce bot backend — manage customers, product catalog, and delivery tracking.")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Raani Admin")))
                .servers(List.of(
                        new Server().url("http://localhost:8080").description("Local dev")));
    }
}
