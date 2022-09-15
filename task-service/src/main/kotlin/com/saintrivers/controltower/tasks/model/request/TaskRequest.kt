package com.saintrivers.controltower.tasks.model.request

import com.saintrivers.controltower.tasks.model.entity.Task
import java.util.*

data class TaskRequest(
    val title: String,
    val description: String,
    val assignedTo: UUID,
    val groupId: UUID,
) {
    fun toEntity() = Task(
        title = title,
        description = description,
        assignedTo = assignedTo,
        groupId = groupId
    )
}