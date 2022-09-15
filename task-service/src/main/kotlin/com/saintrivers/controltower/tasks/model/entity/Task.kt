package com.saintrivers.controltower.tasks.model.entity

import com.saintrivers.controltower.tasks.model.dto.TaskDto
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.Id
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDateTime
import java.util.UUID

@Table("tasks")
data class Task(
    @Id
    var id: UUID? = null,

    @Column("title")
    var title: String? = null,

    @Column("description")
    var description: String? = null,

    @Column("created_by")
    var createdBy: UUID? = null,

    @Column("assigned_to")
    var assignedTo: UUID? = null,

    @Column("group_id")
    var groupId: UUID? = null,

    @Column("status")
    var status: Long? = null,

    @Column("created_date")
    @CreatedDate
    var createdDate: LocalDateTime? = null,

    @Column("last_modified")
    @LastModifiedDate
    var lastModified: LocalDateTime? = null,

    ) {
    fun toDto() = TaskDto(
        id = id!!,
        title = title!!,
        description = description!!,
        groupId = groupId!!,
        status = status!!.toString(),
        createdDate = createdDate!!,
        lastModified = lastModified!!
    )
}
