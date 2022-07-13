package com.kotyk.notepad.util.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenApi() {
        return new OpenAPI()
                .components(new Components())
                .info(new Info()
                        .title("Notepad CRUD API")
                        .description("SpringBoot RESTful service")
                        .version("v0.0.1")
                        .contact(new Contact()
                                .name("Y'Kotyk")
                                .url("https://github.com/makurohashami")));
    }
}
