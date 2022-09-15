package com.saintrivers.controltower.tasks

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.netflix.eureka.EnableEurekaClient

@SpringBootApplication
@EnableEurekaClient
class TaskServiceApplication

fun main(args: Array<String>) {
    runApplication<TaskServiceApplication>(*args)
}