package com.saintrivers.controltower.tasks.service

import com.saintrivers.controltower.tasks.model.dto.TaskDto
import com.saintrivers.controltower.tasks.model.request.TaskRequest
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.util.UUID

interface TaskService {

    fun createTask(taskRequest: TaskRequest, requester: UUID): Mono<TaskDto>
    fun getTasksOfUserInGroup(groupId: UUID, userId: UUID): Flux<TaskDto>
    fun getAllTasksInGroup(groupId: UUID): Flux<TaskDto>
    fun removeTask(taskId: UUID): Mono<Void>
}
