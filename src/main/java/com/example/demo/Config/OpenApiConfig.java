package com.example.demo.Config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI BootCampOpenApi() {
        return new OpenAPI()
                .info(new Info()
                        .title("BootCamp")
                        .description("API para o gerenciamento de carros e marcas")
                        .version("1.0.0")).
                components(new Components().addSecuritySchemes("bearer-jwt", new SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("bearer").description("JWt authentication with bearer Token")));






    }

}