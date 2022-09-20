package com.saintrivers.controltower.tasks.config

import com.netflix.discovery.EurekaClient
import org.springframework.cloud.client.loadbalancer.LoadBalanced
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.client.WebClient

@Configuration
class WebClientConfig(
//    val eurekaClient: EurekaClient
) {

    @Bean("UserClient")
    @LoadBalanced
    fun keycloakClient(): WebClient = WebClient
        .builder()
//        .baseUrl(eurekaClient.getNextServerFromEureka("user-service", false).homePageUrl)
//        .baseUrl("lb:USER-SERVICE")
        .baseUrl("http://user-service:8080")
        .build()
}