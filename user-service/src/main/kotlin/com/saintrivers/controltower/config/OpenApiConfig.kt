package com.saintrivers.controltower.config

import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.annotations.info.Info
import io.swagger.v3.oas.annotations.servers.Server

@OpenAPIDefinition(
    servers = [Server(url = "/", description = "Default Server URL")],
    info = Info(
        title = "Order Up: User Service",
        description = "Spring Boot API for creating and managing users and groups.",
        version = "1.0"
    )
)
class OpenApiConfig