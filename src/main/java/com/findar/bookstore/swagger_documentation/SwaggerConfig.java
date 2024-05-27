package com.findar.bookstore.swagger_documentation;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration


@SecurityScheme(
        name="bearerAuth",
        type = SecuritySchemeType.HTTP,
        scheme = "bearer",
        bearerFormat = "JWT",
        in= SecuritySchemeIn.HEADER
)
@OpenAPIDefinition(
        servers = {
                @Server(description= "Local ENV",
                        url = "http://localhost:8080")
        },
        security = {@SecurityRequirement(name="bearerAuth")}
)


public class SwaggerConfig {

 @Value("${version:1.0.o}")
    private String version;

    @Bean
    public OpenAPI api(){
        return new OpenAPI()
                .info(new Info()
                        .contact(new Contact()
                                .email("aadebisi119@gmail.com")
                                .name("Adebisi Adeyemi"))
                        .title("Book store application")
                        .description("API that provides CRUD operations for Book store management.")
                        .version(version));
    }


    @Bean
    public GroupedOpenApi usersEndpoint(){
        return  GroupedOpenApi
                .builder()
                .group("Admin")
                .pathsToMatch("/api/v1/admin/process/**").build();
    }

    @Bean
    public GroupedOpenApi postEndpoint(){
        return  GroupedOpenApi
                .builder()
                .group("Customer")
                .pathsToMatch("/api/v1/customer/process/**").build();
    }

    @Bean
    public GroupedOpenApi commentEndpoint(){
        return  GroupedOpenApi
                .builder()
                .group("Authentication")
                .pathsToMatch("/api/v1/auth/process/**").build();
    }

    @Bean
    public GroupedOpenApi adminEndpoint(){
        return GroupedOpenApi.builder()
                .group("Book")
                .pathsToMatch("/api/v1/book/process/**").build();
    }
}
