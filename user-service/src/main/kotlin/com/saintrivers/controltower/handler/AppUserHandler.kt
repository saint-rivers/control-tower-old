package com.saintrivers.controltower.handler

import com.saintrivers.controltower.model.request.AppUserProfileRequest
import com.saintrivers.controltower.model.request.AppUserRequest
import com.saintrivers.controltower.service.user.AppUserService
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Mono

@Component
@SecurityRequirement(name = "controlTowerOAuth")
class AppUserHandler(
    val appUserService: AppUserService
) {
    fun findUserById(req: ServerRequest): Mono<ServerResponse> {
        val userId = req.pathVariable("id")
        return appUserService.findById(userId)
            .flatMap {
                ServerResponse.ok().bodyValue(it)
            }
    }

    fun registerUser(req: ServerRequest): Mono<ServerResponse> {
        return req.bodyToMono(AppUserRequest::class.java)
            .flatMap {
                appUserService.registerUser(it)
            }
            .flatMap {
                ServerResponse.ok().bodyValue(it)
            }
            .onErrorResume {
                ServerResponse.badRequest().bodyValue(mapOf("message" to it.localizedMessage))
            }
    }

    fun updateUserProfile(req: ServerRequest): Mono<ServerResponse> =
        req.bodyToMono(AppUserProfileRequest::class.java)
            .flatMap {
                val userId = req.pathVariable("id")
                appUserService.updateUser(userId, it)
            }
            .flatMap {
                ServerResponse.ok().bodyValue(it)
            }
            .onErrorResume {
                ServerResponse.badRequest().bodyValue(mapOf("message" to it.localizedMessage))
            }

    fun deleteUser(req: ServerRequest): Mono<ServerResponse> =
        appUserService.deleteUser(req.pathVariable("id"))
            .flatMap {
                ServerResponse.accepted().build()
            }
            .onErrorResume {
                ServerResponse.badRequest().bodyValue(mapOf("message" to it.localizedMessage))
            }
}