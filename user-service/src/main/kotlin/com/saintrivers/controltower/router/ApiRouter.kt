package com.saintrivers.controltower.router

import com.saintrivers.controltower.handler.AppUserHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.server.RouterFunction
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.router

@Configuration
class ApiRouter(val appUserHandler: AppUserHandler) {

    @Bean
    fun userRouter(): RouterFunction<ServerResponse> = router {
        "/api/v1".nest {
            POST("/users", appUserHandler::registerUser)
        }
    }
}