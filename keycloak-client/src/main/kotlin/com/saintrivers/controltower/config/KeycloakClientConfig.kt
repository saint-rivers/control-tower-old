package com.saintrivers.controltower.config

import org.keycloak.OAuth2Constants
import org.keycloak.admin.client.Keycloak
import org.keycloak.admin.client.KeycloakBuilder
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class KeycloakClientConfig(
    @Value("\${keycloak.credentials.username}")
    private val username: String,
    @Value("\${keycloak.credentials.password}")
    private val password: String,
    @Value("\${keycloak.credentials.secret}")
    private val secretKey: String,
    @Value("\${keycloak.resource}")
    private val clientId: String,
    @Value("\${keycloak.auth-server-url}")
    private val authUrl: String,
    @Value("\${keycloak.realm}")
    private val realm: String,
) {
    @Bean
    fun keycloak(): Keycloak {
        return KeycloakBuilder.builder()
            .grantType(OAuth2Constants.PASSWORD)
            .serverUrl(authUrl)
            .realm(realm)
            .username(username)
            .password(password)
            .clientId(clientId)
            .clientSecret(secretKey)
            .build()
//        return KeycloakBuilder.builder()
//            .grantType(CLIENT_CREDENTIALS)
//            .serverUrl(authUrl)
//            .realm(realm)
//            .clientId(clientId)
//            .clientSecret(secretKey)
//            .build()
    }
}