package com.saintrivers.controltower.gateway.config

import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.annotations.info.Info
import org.springframework.context.annotation.Configuration


@Configuration
@OpenAPIDefinition(
    info =
    Info(title = "Control Tower API", version = "1.0.1", description = "Documentation Control Tower API v1.0")
)
class OpenApiConfig {

//    @Bean
//    fun openApiGroups(
//        locator: RouteDefinitionLocator,
//        swaggerUiParameters: SwaggerUiConfigParameters
//    ): CommandLineRunner {
//        return CommandLineRunner {
//            locator
//                .routeDefinitions.collectList().block()
//                ?.stream()
//                ?.map { obj: RouteDefinition -> obj.id }
//                ?.filter { id: String -> id.matches(Regex(".*-service")) }
//                ?.map { id: String ->
//                    id.replace(
//                        "-service",
//                        ""
//                    )
//                }
//                ?.forEach { group: String? ->
//                    swaggerUiParameters.addGroup(
//                        group
//                    )
//                }
//        }
//    }
}