package com.saintrivers.controltower.mapper

import com.saintrivers.controltower.model.AppUserDto
import org.keycloak.representations.idm.UserRepresentation
import java.util.*

fun UserRepresentation.toDto() = AppUserDto(
    id = UUID.fromString(id),
    username = username,
    firstName = firstName,
    lastName = lastName,
    email = email
)