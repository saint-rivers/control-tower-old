package com.saintrivers.controltower.common.model

data class UserRequest(
    val username: String,
    val password: String,
    val email: String,
    val firstName: String,
    val lastName: String
)
