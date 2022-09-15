package com.saintrivers.controltower.tasks.service

import com.saintrivers.controltower.tasks.model.entity.Task
import org.springframework.data.r2dbc.repository.Query
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import reactor.core.publisher.Mono
import java.util.UUID

interface TaskRepository : ReactiveCrudRepository<Task, UUID> {

    @Query("select name from task_status where id = :id")
    fun selectNameOfTaskStatusId(id: Long): Mono<String>
}