package com.group4.ticketingservice.config

import io.swagger.v3.oas.models.Components
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class OpenApiConfig {
    @Bean
    fun openAPI(): OpenAPI = OpenAPI()
        .components(Components())
        .info(apiInfo())

    private fun apiInfo() = Info()
        .title("프동프동 티켓팅 시스템 API Document")
        .description("티켓팅 시스템 백엔드 API입니다.")
        .version("1.0.0")
}
