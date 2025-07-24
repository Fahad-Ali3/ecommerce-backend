package com.ecommerce.backend.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI myOpenAPI() {

        String schemeName="bearerScheme";

        Contact contact = new Contact();
        contact.setEmail("fahadpathan506@gmail.com");
        contact.setName("Fahad Ali");



        Info info = new Info()
                .title("Ecommerce Application Apis")
                .version("1.0")
                .contact(contact)
                .description("This API exposes endpoints to manage Ecommerce Application APIs.").termsOfService("https://www.bezkoder.com/terms")

                ;

        return new OpenAPI()
                .addSecurityItem(new SecurityRequirement().addList(schemeName))
                .components(new Components()
                        .addSecuritySchemes(schemeName,new SecurityScheme()
                                .name(schemeName)
                                .type(SecurityScheme.Type.HTTP)
                                .bearerFormat("JWT")
                                .scheme("bearer")))
                .info(info);


    }
}