package com.saintrivers.controltower.tasks.service

import com.saintrivers.controltower.common.model.AppUser
import com.saintrivers.controltower.tasks.model.dto.TaskDto
import com.saintrivers.controltower.tasks.model.request.TaskRequest
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.security.core.context.ReactiveSecurityContextHolder
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono
import java.time.LocalDateTime
import java.util.UUID

@Service
class TaskServiceImpl(
    val taskRepository: TaskRepository,
    @Qualifier("UserClient") val userClient: WebClient
) :
    TaskService {

    private val initialTaskStatus = 3L

    fun fetchUser(id: UUID): Mono<AppUser> =
        getAuthenticationPrincipal()
            .flatMap {
                userClient.get()
                    .uri("/api/v1/users/{id}", id)
                    .header("Authorization", "Bearer ${it.tokenValue}")
                    .retrieve()
                    .bodyToMono(AppUser::class.java)
            }

    fun getAuthenticationPrincipal(): Mono<Jwt> =
        ReactiveSecurityContextHolder.getContext()
            .map { it.authentication.principal }
            .cast(Jwt::class.java)

    override fun createTask(taskRequest: TaskRequest, requester: UUID): Mono<TaskDto> {
        val entity = taskRequest.toEntity()
        entity.status = initialTaskStatus
        entity.createdDate = LocalDateTime.now()
        entity.lastModified = entity.createdDate
        entity.createdBy = requester

        val createdBy = fetchUser(entity.createdBy!!)
        val assignedTo = fetchUser(taskRequest.assignedTo)

        return taskRepository.save(entity)
            .zipWith(taskRepository.selectNameOfTaskStatusId(initialTaskStatus))
            .map {
                val res = it.t1.toDto()
                res.status = it.t2
                res
            }
            .zipWith(createdBy).map {
                it.t1.createdBy = it.t2
                it.t1
            }
            .zipWith(assignedTo).map {
                it.t1.assignedTo = it.t2
                it.t1
            }
    }

}
