package com.saintrivers.controltower.service

import com.saintrivers.controltower.model.dto.AppUserDto
import com.saintrivers.controltower.model.request.AppUserRequest
import reactor.core.publisher.Mono

interface AppUserService {

    fun registerUser(req: AppUserRequest): Mono<AppUserDto>
}