package org.saul.ciudadelas.in.cofig.corsconfig;

import jakarta.annotation.Nonnull;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(@Nonnull CorsRegistry registry) {
                registry.addMapping("/**")                      // todos los endpoints
                        .allowedOrigins("*") // frontend permitido
                        .allowedMethods("*")                     // GET, POST, PUT, DELETE…
                        .allowedHeaders("*");                    // todos los headers
            }
        };
    }
}