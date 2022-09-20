package com.saintrivers.controltower.config

import com.netflix.discovery.EurekaClient
import org.springframework.cloud.client.loadbalancer.LoadBalanced
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.client.WebClient

@Configuration
class WebClientConfig(
//    val eurekaClient: EurekaClient
) {

    @Bean("KeycloakClient")
    @LoadBalanced
    fun keycloakClient(): WebClient = WebClient
        .builder()
//        .baseUrl(eurekaClient.getNextServerFromEureka("KEYCLOAK-CLIENT", false).homePageUrl)
//        .baseUrl("lb:KEYCLOAK-CLIENT")
//        .baseUrl("http://localhost:7071")
        .baseUrl("http://keycloak-client:8080")
        .build()
}