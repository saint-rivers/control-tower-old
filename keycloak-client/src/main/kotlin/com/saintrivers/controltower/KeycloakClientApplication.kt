package com.saintrivers.controltower

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.netflix.eureka.EnableEurekaClient

@SpringBootApplication
@EnableEurekaClient
class KeycloakClientApplication

fun main(args: Array<String>) {
    runApplication<KeycloakClientApplication>(*args)
}
