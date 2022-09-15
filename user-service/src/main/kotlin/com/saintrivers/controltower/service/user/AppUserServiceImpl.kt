package com.saintrivers.controltower.service.user

import com.saintrivers.controltower.common.model.UserRequest
import com.saintrivers.controltower.model.dto.AppUserDto
import com.saintrivers.controltower.model.request.AppUserRequest
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono
import java.time.LocalDateTime
import java.util.*

@Service
class AppUserServiceImpl(
    val appUserRepository: AppUserRepository,
    @Qualifier("KeycloakClient") val keycloakClient: WebClient,
) : AppUserService {

    override fun registerUser(req: AppUserRequest): Mono<AppUserDto> {
        val userEntity = req.toEntity()

        val created = keycloakClient.post()
            .uri("/api/user")
            .body(Mono.just(req.toUserRequest()), UserRequest::class.java)
            .retrieve()
            .bodyToMono(AppUserDto::class.java)
            .log()

        return created.flatMap { user ->
            userEntity.createdDate = LocalDateTime.now()
            userEntity.lastModified = userEntity.createdDate
            userEntity.authId = user.id
            appUserRepository.save(userEntity).map { it.toDto() }
        }
            .log()
    }

    override fun findById(id: String): Mono<AppUserDto> =
        appUserRepository.findByAuthId(UUID.fromString(id))
            .map { it.toDto() }


}