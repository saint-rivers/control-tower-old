package com.saintrivers.controltower.keycloak.controller

import com.saintrivers.controltower.keycloak.service.group.GroupService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/group")
class GroupController(
    private val groupService: GroupService
) {
    @GetMapping
    fun findAll() = groupService.findAll()

    @PostMapping
    fun createGroup(@RequestParam name: String) = groupService.create(name)
}