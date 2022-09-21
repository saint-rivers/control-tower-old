package com.saintrivers.controltower.common.model

import java.time.LocalDateTime
import java.util.UUID

data class Group(
    val id:UUID,
    val name: String,
    val image: String,
    val createdDate: LocalDateTime
)