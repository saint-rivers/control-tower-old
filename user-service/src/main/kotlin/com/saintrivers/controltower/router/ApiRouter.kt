package com.saintrivers.controltower.router

import com.saintrivers.controltower.handler.AppUserHandler
import com.saintrivers.controltower.handler.GroupHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.server.RouterFunction
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.router

@Configuration
class ApiRouter(val appUserHandler: AppUserHandler, val groupHandler: GroupHandler) {

    @Bean
    fun userRouter(): RouterFunction<ServerResponse> = router {
        "/api/v1".nest {
            POST("/users", appUserHandler::registerUser)
            POST("/groups", groupHandler::createGroup)
            POST("/groups/members", groupHandler::addGroupMember)
        }
    }
}