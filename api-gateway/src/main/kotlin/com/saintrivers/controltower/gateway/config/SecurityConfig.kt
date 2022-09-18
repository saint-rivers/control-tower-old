package com.saintrivers.controltower.gateway.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.config.web.server.invoke
import org.springframework.security.web.server.SecurityWebFilterChain
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.reactive.CorsConfigurationSource
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource


@Configuration
@EnableWebFluxSecurity
class SecurityConfig {

    @Bean
    fun securityFilter(http: ServerHttpSecurity): SecurityWebFilterChain {
        return http.invoke {
            authorizeExchange {
//                authorize("/tasks/**", permitAll)
//                authorize("/users/**", permitAll)
//                authorize("/tasks/api/v1/tasks/**", authenticated)
//                authorize("/users/api/v1/users/**", authenticated)
//                authorize("/actuator/**", permitAll)
//                authorize("/webjars/**", permitAll)
//                authorize("/v3/api-docs/**", permitAll)
                authorize(anyExchange, permitAll)
            }
            csrf { disable() }
            oauth2ResourceServer {
                jwt {}
            }
        }
    }

    @Bean
    fun corsConfigurationSource(): CorsConfigurationSource {
        val configuration = CorsConfiguration()
        configuration.allowedOrigins = listOf("*")
        configuration.allowedMethods = listOf("GET", "POST")
        configuration.allowedHeaders = listOf("Authorization", "Content-Type", "x-requested-with", "X-XSRF-TOKEN", "x-forwarded-host", "x-forwarded-proto")
        val source = UrlBasedCorsConfigurationSource()
        source.registerCorsConfiguration("/**", configuration)
        return source
    }


}
