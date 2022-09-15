package com.saintrivers.controltower.common.model

import java.util.UUID

data class AppUser(
    val id: UUID,
    val username: String,
    val email: String,
    val firstName: String,
    val lastName: String
)

