package com.saintrivers.controltower.service.user

import com.saintrivers.controltower.model.dto.AppUserDto
import com.saintrivers.controltower.model.request.AppUserRequest
import reactor.core.publisher.Mono

interface AppUserService {

    fun registerUser(req: AppUserRequest): Mono<AppUserDto>
    fun findById(id: String): Mono<AppUserDto>
    fun deleteUser(id: String): Mono<Void>
    fun updateUser(id: String, req: AppUserRequest): Mono<AppUserDto>
}