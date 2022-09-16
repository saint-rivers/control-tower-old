package com.saintrivers.controltower.config

import org.springframework.cloud.client.loadbalancer.LoadBalanced
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.client.WebClient

@Configuration
class WebClientConfig {

    @Bean("KeycloakClient")
    @LoadBalanced
    fun keycloakClient(): WebClient = WebClient
        .builder()
//        .baseUrl(discoveryClient.getNextServerFromEureka("keycloak-client", false).homePageUrl)
        .baseUrl("lb:KEYCLOAK-CLIENT")
        .build()
}