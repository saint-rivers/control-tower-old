package com.saintrivers.controltower.tasks.model.dto

import com.saintrivers.controltower.common.model.AppUser
import java.time.LocalDateTime
import java.util.*

data class TaskDto(
    val id: UUID,
    val title: String,
    val description: String,
    var createdBy: AppUser? = null,
    var assignedTo: AppUser? = null,
    val groupId: UUID,
    var status: String,
    val createdDate: LocalDateTime,
    val lastModified: LocalDateTime
)