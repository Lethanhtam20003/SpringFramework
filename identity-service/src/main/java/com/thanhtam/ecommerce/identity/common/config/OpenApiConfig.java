package com.thanhtam.ecommerce.identity.common.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;


@Configuration
public class OpenApiConfig {
    private static final String SECURITY_SCHEME_NAME = "BearerAuth";

    @Bean
    public OpenAPI customOpenAPI(  @Value("${openapi.service.title:Identity Service API}") String title,
                                   @Value("${openapi.service.version:v1.0.0}") String version,
                                   @Value("${openapi.service.description:Identity and Access Management Service}") String description,
                                   @Value("${openapi.service.server-url:http://localhost:8080}") String serverUrl) {
        return new OpenAPI()
                .info(new Info()
                        .title(title)
                        .version(version)
                        .description(description)
                        .contact(new Contact()
                                .name("Core Backend Team")
                                .email("backend-team@yourdomain.com"))
                        .license(new License().name("Apache 2.0").url("https://springdoc.org")))
                .servers(List.of(
                        new Server().url(serverUrl).description("Current Environment Server"),
                        new Server().url("http://localhost:8080").description("Local Development"),
                        new Server().url("https://dev-api.yourdomain.com/identity").description("Dev Gateway")
                ))
                // Cấu hình Global Security để Swagger UI có nút "Authorize"
                .addSecurityItem(new SecurityRequirement().addList(SECURITY_SCHEME_NAME))
                .components(new Components()
                        .addSecuritySchemes(SECURITY_SCHEME_NAME, new SecurityScheme()
                                .name(SECURITY_SCHEME_NAME)
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")
                                .description("Nhập JWT Token theo định dạng: Bearer {token} (Swagger sẽ tự thêm prefix 'Bearer ')")));
    }
}
