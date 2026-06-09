package com.af.estrategiafinanciera.infrastructure.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
public class CorsConfig {

    @Value("${FRONTEND_URL:http://localhost:5173}")
    private String frontendUrl;

    @Bean
    public CorsConfigurationSource corsConfigurationSource(){
        CorsConfiguration config = new CorsConfiguration();

        config.setAllowedOrigins(List.of(
                "http://localhost:5173",  // React dev server (Vite)
                "http://localhost:3000"   // React dev server alternativo
        ));

        config.setAllowedMethods(List.of(
                "GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"
        ));

        // Headers expuestos al frontend
        config.setExposedHeaders(List.of(
                "Authorization"
        ));

        // Permite enviar cookies y credenciales
        config.setAllowCredentials(true);

        // Tiempo que el navegador cachea la configuración CORS (1 hora)
        config.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source =
                new UrlBasedCorsConfigurationSource();

        // Aplica esta configuración a todas las rutas
        source.registerCorsConfiguration("/**", config);

        return source;
    }
}
