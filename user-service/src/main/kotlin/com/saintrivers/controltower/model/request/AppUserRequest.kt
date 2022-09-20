package com.saintrivers.controltower.model.request

import com.saintrivers.controltower.common.model.UserRequest
import com.saintrivers.controltower.model.entity.AppUser

data class AppUserRequest(
    val username: String,
    val email: String,
    val profileImage: String,
    val firstName: String,
    val lastName: String,
    val password: String
) {
    fun toEntity() = AppUser(
        username = username,
        email = email,
        firstName = firstName,
        lastName = lastName,
        profileImage = profileImage
    )

    fun toUserRequest() = UserRequest(
        username = username,
        email = email,
        firstName = firstName,
        lastName = lastName,
        password = password
    )
}

data class AppUserProfileRequest(
    val username: String,
    val profileImage: String,
    val firstName: String,
    val lastName: String,
) {
    fun toEntity() = AppUser(
        username = username,
        firstName = firstName,
        lastName = lastName,
        profileImage = profileImage
    )
}