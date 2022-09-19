package com.saintrivers.controltower.tasks.router

import com.saintrivers.controltower.tasks.handler.TaskHandler
import com.saintrivers.controltower.tasks.router.openapi.TaskRouterOperations
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.server.RouterFunction
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.router

@Configuration
class ApiRouter(val taskHandler: TaskHandler) {

    @Bean
    @TaskRouterOperations
    fun taskRouter(): RouterFunction<ServerResponse> = router {
        "/api/v1".nest {
            POST("/tasks", taskHandler::createTask)
            GET("/tasks/group/{id}", taskHandler::findTasksOfUserInGroup)
            DELETE("/tasks/{id}", taskHandler::deleteTask)
        }
    }
}