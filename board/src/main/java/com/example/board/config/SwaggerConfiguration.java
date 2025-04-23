package com.example.board.config;


import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
    info = @Info(
        title = "게시판",
        description = "게시판",
        version = "v1"
    )
)

@Configuration
public class SwaggerConfiguration {

  @Bean
  public OpenAPI openAPI() {
    SecurityScheme auth = new SecurityScheme()
        .type(SecurityScheme.Type.APIKEY).in(SecurityScheme.In.COOKIE).name("JSESSIONID");
    SecurityRequirement securityRequirement = new SecurityRequirement().addList("basicAuth");

    return new OpenAPI()
        .components(new Components().addSecuritySchemes("basicAuth", auth))
        .addSecurityItem(securityRequirement);
  }
}
