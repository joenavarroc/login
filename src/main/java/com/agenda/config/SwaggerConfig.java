package com.agenda.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;

@Configuration  // Indica que esta clase es una clase de configuración de Spring
public class SwaggerConfig {

    // El método @Bean indica que esta es una definición de un "bean" para el contexto de Spring
    @Bean
    public OpenAPI customOpenAPI() {
        // Creamos un objeto OpenAPI que es el que usará Swagger para generar la documentación
        return new OpenAPI()
            .info(new Info()  // Información principal de la API que aparecerá en la documentación
                .title("Agenda API")  // El título de la API que aparecerá en la documentación
                .version("1.0.0")  // Versión de la API
                .description("Documentación de la API de la agenda")  // Descripción de la API
                .contact(new Contact()  // Información de contacto del propietario de la API
                    .name("Nombre del contacto")  // Nombre del contacto de la API
                    .email("email@example.com")  // Correo electrónico de contacto
                    .url("http://example.com")));  // URL del contacto (puede ser una página web)
    }
}
