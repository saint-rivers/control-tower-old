package com.saintrivers.controltower.model

import java.time.LocalDateTime
import java.util.UUID


data class AppUserDto(
    val id: UUID,
    val username: String,
    val email: String,
    val firstName: String,
    val lastName: String,
    val profileImage: String? = null,
    val createdDate: LocalDateTime? = null,
    val lastModified: LocalDateTime? = null,
)

