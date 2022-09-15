package com.saintrivers.controltower.service

import com.saintrivers.controltower.model.AppUserDto
import com.saintrivers.controltower.model.AppUserRequest
import com.saintrivers.controltower.model.UserRequest
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono
import java.time.LocalDateTime

@Service
class AppUserServiceImpl(
    val appUserRepository: AppUserRepository,
    @Qualifier("KeycloakClient") val keycloakClient: WebClient
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
}