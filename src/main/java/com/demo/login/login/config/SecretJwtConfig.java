package com.demo.login.login.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:secret.properties")
public class SecretJwtConfig {
    // Esta configuración asegura que las propiedades de secret.properties estén disponibles para @Value.
}
