package com.saintrivers.controltower.tasks.handler

import com.saintrivers.controltower.tasks.model.dto.TaskDto
import com.saintrivers.controltower.tasks.model.request.TaskRequest
import com.saintrivers.controltower.tasks.service.TaskService
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import org.springframework.security.core.context.ReactiveSecurityContextHolder
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Mono
import java.util.*

@Component
@SecurityRequirement(name = "controlTowerOAuth")
class TaskHandler(val taskService: TaskService) {
    fun getAuthenticationPrincipal(): Mono<Jwt> =
        ReactiveSecurityContextHolder.getContext()
            .map { it.authentication.principal }
            .cast(Jwt::class.java)

    fun findTasksOfUserInGroup(req: ServerRequest): Mono<ServerResponse> {
        val groupId = UUID.fromString(req.queryParam("group").get())
        val userId = UUID.fromString(req.queryParam("user").get())

        return ServerResponse.ok().body(
            taskService.getTasksOfUserInGroup(groupId, userId),
            TaskDto::class.java
        )
    }

    fun createTask(req: ServerRequest): Mono<ServerResponse> =
        req.bodyToMono(TaskRequest::class.java).zipWith(getAuthenticationPrincipal())
            .flatMap {
                val sub = UUID.fromString(it.t2.claims["sub"].toString())
                taskService.createTask(it.t1, sub)
            }
            .flatMap {
                ServerResponse.ok().bodyValue(it)
            }
            .onErrorResume {
                ServerResponse.badRequest().bodyValue(mapOf("message" to it.localizedMessage))
            }
}