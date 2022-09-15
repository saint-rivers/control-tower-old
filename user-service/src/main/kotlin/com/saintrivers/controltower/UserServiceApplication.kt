package com.saintrivers.controltower

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.netflix.eureka.EnableEurekaClient
import org.springframework.data.r2dbc.config.EnableR2dbcAuditing

@SpringBootApplication
@EnableEurekaClient
@EnableR2dbcAuditing
class UserServiceApplication

fun main(args: Array<String>) {
    runApplication<UserServiceApplication>(*args)
}
