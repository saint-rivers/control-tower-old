package com.saintrivers.controltower.tasks.config

import com.netflix.discovery.EurekaClient
import io.netty.resolver.DefaultAddressResolverGroup
import org.springframework.cloud.client.loadbalancer.LoadBalanced
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.client.reactive.ReactorClientHttpConnector
import org.springframework.web.reactive.function.client.WebClient
import reactor.netty.http.client.HttpClient

@Configuration
class WebClientConfig(val discoveryClient: EurekaClient) {

    @Bean
    fun httpClientReactor(): HttpClient = HttpClient.create().resolver(DefaultAddressResolverGroup.INSTANCE)

    @Bean("UserClient")
    @LoadBalanced
    fun keycloakClient(httpClient: HttpClient): WebClient = WebClient
        .builder()
        .clientConnector(ReactorClientHttpConnector(httpClient))
        .baseUrl(discoveryClient.getNextServerFromEureka("user-service", false).homePageUrl)
        .build()
}