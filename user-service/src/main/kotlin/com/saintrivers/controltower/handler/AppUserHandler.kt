package com.saintrivers.controltower.handler

import com.saintrivers.controltower.model.request.AppUserRequest
import com.saintrivers.controltower.service.user.AppUserService
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Mono

@Component
class AppUserHandler(
    val appUserService: AppUserService
) {
    fun findUserById(req: ServerRequest): Mono<ServerResponse> =
        appUserService.findById(req.pathVariable("id"))
            .flatMap {
                ServerResponse.ok().bodyValue(it)
            }

    fun registerUser(req: ServerRequest): Mono<ServerResponse> {
        return req.bodyToMono(AppUserRequest::class.java)
            .flatMap {
                appUserService.registerUser(it)
            }
            .log()
            .flatMap {
                ServerResponse.ok().bodyValue(it)
            }
    }

}