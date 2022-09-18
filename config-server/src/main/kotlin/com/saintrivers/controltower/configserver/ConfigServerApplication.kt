package com.saintrivers.controltower.configserver

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.config.server.EnableConfigServer
import org.springframework.cloud.context.config.annotation.RefreshScope

@SpringBootApplication
@EnableConfigServer
@RefreshScope
class ConfigServerApplication

fun main(args: Array<String>) {
    runApplication<ConfigServerApplication>(*args)
}
