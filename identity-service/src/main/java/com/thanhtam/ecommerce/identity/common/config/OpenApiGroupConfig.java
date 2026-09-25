package com.thanhtam.ecommerce.identity.common.config;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiGroupConfig {
    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("1. public-api")
                .pathsToMatch("/api/v1/**")
                .build();
    }
    @Bean
    public GroupedOpenApi adminApi() {
        return GroupedOpenApi.builder()
                .group("2. Internal & Admin Management APIs")
                .pathsToMatch("/api/v1/admin/**", "/api/v1/roles/**", "/api/v1/permissions/**")
                .build();
    }
}
