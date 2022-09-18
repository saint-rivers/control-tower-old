package com.saintrivers.controltower.router

import com.saintrivers.controltower.handler.AppUserHandler
import com.saintrivers.controltower.handler.GroupHandler
import com.saintrivers.controltower.router.openapi.GroupRouterOperations
import com.saintrivers.controltower.router.openapi.UserRouterOperations
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.server.RouterFunction
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.router

@Configuration
class ApiRouter(val appUserHandler: AppUserHandler, val groupHandler: GroupHandler) {

    @Bean
    @UserRouterOperations
    fun userRouter(): RouterFunction<ServerResponse> = router {
        "/api/v1".nest {
            POST("/users", appUserHandler::registerUser)
            GET("/users/{id}", appUserHandler::findUserById)
        }
    }

    @Bean
    @GroupRouterOperations
    fun groupRouter(): RouterFunction<ServerResponse> = router {
        "/api/v1".nest {
            POST("/groups", groupHandler::createGroup)
            POST("/groups/members", groupHandler::addGroupMember)
        }
    }
}