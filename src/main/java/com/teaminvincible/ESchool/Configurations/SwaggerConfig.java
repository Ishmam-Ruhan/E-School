package com.teaminvincible.ESchool.Configurations;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@OpenAPIDefinition(info = @Info(
        title = "E - School API Documentation",
        description = "E-School is a project for THERAP JavaFest. This is a documentation for this projects available API\n\n" +
                "All end-points are protected. To use this documentation:\n\n" +
                "auth-controller -> REGISTER Endpoint -> register with all information's -> " +
                "SIGN-IN endpoint -> sign in with credential\n\n" +
                "Next you'll get the JWT token. Use this jwt token to authorize with 'bearer ' suffix."
))
public class SwaggerConfig {
    @Bean
    public OpenAPI e_SchoolOpenApi() {
        return new OpenAPI()
                .addSecurityItem(new SecurityRequirement().addList("BASIC"))
                .components( new Components()
                        .addSecuritySchemes("Authorization", securityScheme("Authorization"))
                )
                .addSecurityItem(new SecurityRequirement().addList("Authorization"));
    }

    private SecurityScheme securityScheme(String name) {
        return new io.swagger.v3.oas.models.security.SecurityScheme()
                .type(SecurityScheme.Type.APIKEY)
                .in(SecurityScheme.In.HEADER)
                .name(name);
    }

}
