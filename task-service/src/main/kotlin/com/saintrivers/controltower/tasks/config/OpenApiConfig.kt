package com.saintrivers.controltower.tasks.config

import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType
import io.swagger.v3.oas.annotations.info.Info
import io.swagger.v3.oas.annotations.security.OAuthFlow
import io.swagger.v3.oas.annotations.security.OAuthFlows
import io.swagger.v3.oas.annotations.security.SecurityScheme
import io.swagger.v3.oas.annotations.servers.Server

@OpenAPIDefinition(
    servers = [Server(url = "/", description = "Default Server URL")],
    info = Info(
        title = "Control Tower: Task Service",
        description = "Spring Boot API for creating and managing tasks.",
        version = "1.0.1"
    )
)
@SecurityScheme(
    name = "controlTowerOAuth",
    scheme = "oauth2",
    type = SecuritySchemeType.OAUTH2,
    `in` = SecuritySchemeIn.HEADER,
    flows = OAuthFlows(
        clientCredentials = OAuthFlow(
            tokenUrl = "https://auth.saintrivers.tech/auth/realms/control-tower/protocol/openid-connect/token"
        ),
        password = OAuthFlow(
            tokenUrl = "https://auth.saintrivers.tech/auth/realms/control-tower/protocol/openid-connect/token",
        )
    )
)
class OpenApiConfig