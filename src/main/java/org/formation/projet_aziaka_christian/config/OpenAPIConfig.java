package org.formation.projet_aziaka_christian.config;

import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAPIConfig {

    @Bean
    public OpenAPI simpleCashAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("SimpleCashSI - API Documentation")
                        .description("API du projet bancaire : clients, comptes, conseillers, audit.")
                        .version("1.0.0"));
    }
}
