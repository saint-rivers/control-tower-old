package com.saintrivers.controltower.service

import com.saintrivers.controltower.model.AppUserDto
import com.saintrivers.controltower.model.AppUserRequest
import reactor.core.publisher.Mono

interface AppUserService {

    fun registerUser(req: AppUserRequest): Mono<AppUserDto>
}