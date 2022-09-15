package com.saintrivers.controltower

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.client.discovery.EnableDiscoveryClient

@SpringBootApplication
@EnableDiscoveryClient
class KeycloakClientApplication

fun main(args: Array<String>) {
    runApplication<KeycloakClientApplication>(*args)
}
