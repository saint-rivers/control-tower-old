package com.saintrivers.controltower.keycloak.controller

import com.saintrivers.controltower.common.model.AppUser
import com.saintrivers.controltower.common.model.UserRequest
import com.saintrivers.controltower.keycloak.service.role.RoleService
import com.saintrivers.controltower.keycloak.service.user.UserService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.UUID

@RestController
@RequestMapping("/api/user")
class UserController(
    private val userService: UserService,
    private val roleService: RoleService
) {
    @GetMapping
    fun findAll() =
        userService.findAll()

    @GetMapping("/{id}")
    fun findById(@PathVariable id: String): AppUser {
        return userService.findById(id)
    }

    @DeleteMapping("/{id}")
    fun deleteById(@PathVariable id: String): ResponseEntity<UUID> {
        userService.delete(id)
        return ResponseEntity.accepted().body(UUID.fromString(id))
    }

    @GetMapping("/username/{username}")
    fun findByUsername(@PathVariable username: String) =
        userService.findByUsername(username)

    @GetMapping("/email/{email}")
    fun findByEmail(@PathVariable email: String) =
        userService.findByEmail(email)

    @PostMapping
    fun create(@RequestBody userRequest: UserRequest): ResponseEntity<AppUser> {
        val response = userService.create(userRequest)

        println(response)
        println(response)
        println(response)
        println(response)
        println("\n")

        println(response.entity)
        println(response.entity)
        println(response.entity)
        println(response.entity)
        println("\n")

        println(response.status)
        println(response.status)
        println(response.status)
        println(response.status)

        if (response.status != 201)
            throw RuntimeException("User was not created")

//        return ResponseEntity.created(response.location).build()
        val createdUser = userService.findByEmail(userRequest.email)[0]
        return ResponseEntity.ok().body(createdUser)
    }

    @PostMapping("/{userId}/group/{groupId}")
    fun assignToGroup(
        @PathVariable userId: String,
        @PathVariable groupId: String
    ) {
        userService.assignToGroup(userId, groupId)
    }

    @PostMapping("/{userId}/role/{roleName}")
    fun assignRole(
        @PathVariable userId: String,
        @PathVariable roleName: String
    ) {
        val role = roleService.findByName(roleName)

        userService.assignRole(userId, role)
    }
}
