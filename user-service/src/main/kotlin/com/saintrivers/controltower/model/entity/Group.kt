package com.saintrivers.controltower.model.entity

import com.saintrivers.controltower.model.dto.GroupDto
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDateTime
import java.util.UUID

@Table("groups")
class Group(

    @Id
    @Column("id")
    var id: UUID? = null,

    @Column("group_name")
    var name: String? = null,

    @Column("group_image")
    var image: String? = null,

    @CreatedDate
    @Column("created_date")
    var createdDate: LocalDateTime? = null
) {
    fun toDto() = GroupDto(
        id = id!!,
        name = name!!,
        image = image!!,
        createdDate = createdDate!!
    )
}