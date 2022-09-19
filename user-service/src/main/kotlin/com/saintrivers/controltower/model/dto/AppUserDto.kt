package com.saintrivers.controltower.model.dto

import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import java.time.LocalDateTime
import java.util.UUID


data class AppUserDto(
    val id: UUID,
    val username: String,
    val email: String,
    val firstName: String,
    val lastName: String,
    val profileImage: String? = null,
    @CreatedDate
    val createdDate: LocalDateTime? = null,
    @LastModifiedDate
    val lastModified: LocalDateTime? = null,
    val isEnabled: Boolean
)

