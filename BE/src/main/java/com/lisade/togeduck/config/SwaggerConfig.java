package com.lisade.togeduck.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
    info = @Info(title = "투게덕 API 명세서", description = "지방 덕후들을 위한 수요응답 교통 서비스")
)
@RequiredArgsConstructor
@Configuration
public class SwaggerConfig {

    @Bean
    public GroupedOpenApi togeduckOpenApi() {
        String[] paths = {"/**"};
        return GroupedOpenApi.builder().group("투게덕 API v1").pathsToMatch(paths).build();
    }
}
