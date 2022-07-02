package com.teaminvincible.ESchool.Configurations;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@OpenAPIDefinition(info = @Info(
        title = "E - School API Documentation",
        description = "E-School is a project for THERAP JavaFest. This is a documentation for this projects available API"
))
public class SwaggerConfig {
}
