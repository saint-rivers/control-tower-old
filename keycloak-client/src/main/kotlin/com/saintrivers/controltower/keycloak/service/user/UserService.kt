package com.saintrivers.controltower.keycloak.service.user

import com.saintrivers.controltower.common.exception.user.UserAlreadyExistsException
import com.saintrivers.controltower.common.model.AppUser
import com.saintrivers.controltower.common.model.UserRequest
import org.keycloak.admin.client.Keycloak
import org.keycloak.representations.idm.CredentialRepresentation
import org.keycloak.representations.idm.RoleRepresentation
import org.keycloak.representations.idm.UserRepresentation
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import com.saintrivers.controltower.mapper.toDto
import javax.ws.rs.core.Response


@Service
class UserService(
    private val keycloak: Keycloak,
    @Value("\${keycloak.realm}")
    private val realm: String
) {
    fun findAll(): List<AppUser> =
        keycloak
            .realm(realm)
            .users()
            .list()
            .map { it.toDto() }

    fun findByUsername(username: String): List<AppUser> =
        keycloak
            .realm(realm)
            .users()
            .search(username)
            .map { it.toDto() }

    fun findByEmail(email: String): List<AppUser> =
        keycloak
            .realm(realm)
            .users()
            .list()
            .filter { it.email.contains(email, true) }
            .map { it.toDto() }

    fun findById(id: String): AppUser =
        keycloak
            .realm(realm)
            .users()
            .get(id)
            .toRepresentation()
            .toDto()

    fun assignToGroup(userId: String, groupId: String) {
        keycloak
            .realm(realm)
            .users()
            .get(userId)
            .joinGroup(groupId)
    }

    fun assignRole(userId: String, roleRepresentation: RoleRepresentation) {
        keycloak
            .realm(realm)
            .users()
            .get(userId)
            .roles()
            .realmLevel()
            .add(listOf(roleRepresentation))
    }

    fun delete(userId: String): Response {
        return keycloak
            .realm(realm)
            .users()
            .delete(userId)
    }

    fun create(request: UserRequest): Response {
        val existingUser = findByEmail(request.email)
        if (existingUser.size == 0) {
            val password = preparePasswordRepresentation(request.password)
            val user = prepareUserRepresentation(request, password)

            return keycloak
                .realm(realm)
                .users()
                .create(user)

        } else throw UserAlreadyExistsException()
    }

    private fun preparePasswordRepresentation(
        password: String
    ): CredentialRepresentation {
        val cR = CredentialRepresentation()
        cR.isTemporary = false
        cR.type = CredentialRepresentation.PASSWORD
        cR.value = password
        return cR
    }

    private fun prepareUserRepresentation(
        request: UserRequest,
        cR: CredentialRepresentation
    ): UserRepresentation {
        val newUser = UserRepresentation()
        newUser.username = request.username
        newUser.email = request.email
        newUser.firstName = request.firstName
        newUser.lastName = request.lastName
        newUser.credentials = listOf(cR)
        newUser.isEnabled = true
        return newUser
    }
}