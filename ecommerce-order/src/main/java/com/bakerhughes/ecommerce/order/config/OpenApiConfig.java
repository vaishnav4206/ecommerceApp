package com.bakerhughes.ecommerce.order.config;


import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Order Service API")
                        .version("1.0")
                        .description("API for managing order")
                        .contact(new Contact()
                                .name("Vaishnav")
                                .email("vaishnavvijayachandran@gmail.com")
                        )
                );
    }
}