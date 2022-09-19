package com.saintrivers.controltower.tasks.service

import com.saintrivers.controltower.common.exception.NotResourceOwnerException
import com.saintrivers.controltower.common.model.AppUser
import com.saintrivers.controltower.tasks.exception.TaskNotFoundException
import com.saintrivers.controltower.tasks.model.dto.TaskDto
import com.saintrivers.controltower.tasks.model.entity.Task
import com.saintrivers.controltower.tasks.model.request.TaskRequest
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.security.core.context.ReactiveSecurityContextHolder
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Flux
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
        entity.assignedTo = requester

        return taskRepository.save(entity)
            .zipWith(taskRepository.selectNameOfTaskStatusId(initialTaskStatus))
            .map {
                val res = it.t1.toDto()
                res.status = it.t2
                res
            }
            .fetchAndZipRelatedUsers(entity)
    }

    fun Mono<TaskDto>.fetchAndZipRelatedUsers(entity: Task): Mono<TaskDto> {
        val createdBy = fetchUser(entity.createdBy!!)
        val assignedTo = fetchUser(entity.assignedTo!!)
        return this
            .zipWith(createdBy).map {
                it.t1.createdBy = it.t2
                it.t1
            }
            .zipWith(assignedTo).map {
                it.t1.assignedTo = it.t2
                it.t1
            }
    }


    override fun getTasksOfUserInGroup(groupId: UUID, userId: UUID): Flux<TaskDto> {
        val taskFlux = taskRepository
            .findAllByGroupIdAndAssignedTo(
                groupId = groupId,
                assignedTo = userId
            )

        return taskFlux
            .map { it.toDto() }
            .zipWith(taskFlux)
            .flatMap {
                Mono.just(it.t1)
                    .fetchAndZipRelatedUsers(
                        Task(createdBy = it.t2.createdBy, assignedTo = it.t2.assignedTo)
                    )
            }
    }

    override fun getAllTasksInGroup(groupId: UUID): Flux<TaskDto> {
        return taskRepository.findAllByGroupId(groupId)
            .flatMap {
                val dto = it.toDto()
                Mono.just(dto).fetchAndZipRelatedUsers(
                    Task(createdBy = it.createdBy, assignedTo = it.assignedTo)
                )
            }
            .log()
    }

    override fun removeTask(taskId: UUID): Mono<Void> {
        return taskRepository.findById(taskId)
            .switchIfEmpty(Mono.error(TaskNotFoundException()))
            .flatMap { task ->
                getAuthenticationPrincipal().flatMap {
                    if (it.claims["sub"].toString() == task.createdBy.toString())
                        taskRepository.delete(task)
                    else Mono.error(NotResourceOwnerException())
                }
            }

    }


}
