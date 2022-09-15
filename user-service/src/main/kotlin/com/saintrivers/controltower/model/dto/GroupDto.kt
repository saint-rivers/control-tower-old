package com.saintrivers.controltower.model.dto

import java.time.LocalDateTime
import java.util.UUID

data class GroupDto(
    val id:UUID,
    val name: String,
    val image: String,
    val createdDate: LocalDateTime
)