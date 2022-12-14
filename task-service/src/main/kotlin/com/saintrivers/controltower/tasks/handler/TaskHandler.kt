package com.saintrivers.controltower.tasks.handler

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
import java.util.stream.Collectors

@Component
@SecurityRequirement(name = "controlTowerOAuth")
class TaskHandler(val taskService: TaskService) {

    fun Throwable.toErrorResponse(): Mono<ServerResponse> =
        ServerResponse.badRequest().bodyValue(mapOf("message" to this.localizedMessage))

    fun getAuthenticationPrincipal(): Mono<Jwt> =
        ReactiveSecurityContextHolder.getContext()
            .map { it.authentication.principal }
            .cast(Jwt::class.java)

    fun findTasksOfUserInGroup(req: ServerRequest): Mono<ServerResponse> {
        val groupId = req.queryParam("group")
        val userIdRequest = req.queryParam("assignedto")

        val response =
            if (userIdRequest.isPresent)
                taskService
                    .getTasksOfUserInGroup(
                        UUID.fromString(groupId.get()),
                        UUID.fromString(userIdRequest.get())
                    )
                    .collect(Collectors.toList())
                    .flatMap {
                        ServerResponse.ok().bodyValue(it)
                    }
            else
                taskService
                    .getAllTasksInGroup(UUID.fromString(groupId.get()))
                    .collect(Collectors.toList())
                    .flatMap {
                        ServerResponse.ok().bodyValue(it)
                    }

        return response.onErrorResume {
            it.toErrorResponse()
        }
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

    fun deleteTask(req: ServerRequest): Mono<ServerResponse> =
        taskService.removeTask(UUID.fromString(req.pathVariable("id")))
            .flatMap { ServerResponse.accepted().build() }
            .onErrorResume {
                val error = Mono.just(mapOf("message" to it.localizedMessage))
                ServerResponse.badRequest().body(error, UUID::class.java)
            }
}