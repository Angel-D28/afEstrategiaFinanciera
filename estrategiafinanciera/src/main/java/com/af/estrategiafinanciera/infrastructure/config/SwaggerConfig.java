package com.af.estrategiafinanciera.infrastructure.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "AF Estrategia Financiera API",
                version = "1.0.0",
                description = "API REST para la plataforma de asesoría financiera AF. " +
                        "Gestión de usuarios, planes, suscripciones y pagos.",
                contact = @Contact(
                        name = "Fays Santiafo Diaz",
                        email = "gerencia@afestrategiafinanciera.com"

                )
        ),
        servers = {
                @Server(url = "http://localhost:8080" , description = "Servidor Local")
        },
        security = @SecurityRequirement(name = "bearerAuth")
)
@SecurityScheme(
        name = "bearerAuth",
        description = "JWT token de autenticación. Formato: Bearer {token}",
        scheme = "bearer",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        in = SecuritySchemeIn.HEADER
)
public class SwaggerConfig {}
