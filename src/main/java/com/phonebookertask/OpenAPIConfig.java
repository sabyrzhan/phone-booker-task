package com.phonebookertask;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAPIConfig {
    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI().info(new Info()
            .title("Phone Booker REST API")
            .version("1.0")
            .description("This API exposes endpoints to manage phone bookings.")
            .termsOfService("https://www.phonebookerapp.com/terms")
            .license(new License().name("MIT License").url("https://wwww.phonebookerapp.com/licenses/mit/"))
        );
    }
}
