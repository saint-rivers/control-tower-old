package com.saintrivers.controltower.mapper

import com.saintrivers.controltower.common.model.AppUser
import org.keycloak.representations.idm.UserRepresentation
import java.util.*

fun UserRepresentation.toDto() = AppUser(
    id = UUID.fromString(id),
    username = username,
    firstName = firstName,
    lastName = lastName,
    email = email
)