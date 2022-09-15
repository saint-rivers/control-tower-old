package com.saintrivers.controltower.keycloak.user

data class UserRequest(
    val username: String,
    val password: String,
    val email: String,
    val firstName: String,
    val lastName: String
)
