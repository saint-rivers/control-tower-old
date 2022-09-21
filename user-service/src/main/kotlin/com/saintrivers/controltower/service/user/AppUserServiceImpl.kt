package com.saintrivers.controltower.service.user

import com.saintrivers.controltower.common.exception.user.*
import com.saintrivers.controltower.common.model.UserRequest
import com.saintrivers.controltower.model.dto.AppUserDto
import com.saintrivers.controltower.model.entity.AppUser
import com.saintrivers.controltower.model.request.AppUserProfileRequest
import com.saintrivers.controltower.model.request.AppUserRequest
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.security.core.context.ReactiveSecurityContextHolder
import org.springframework.security.oauth2.jwt.Jwt
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

    fun getAuthenticationPrincipal(): Mono<Jwt> =
        ReactiveSecurityContextHolder.getContext()
            .map { it.authentication.principal }
            .cast(Jwt::class.java)

    override fun registerUser(req: AppUserRequest): Mono<AppUserDto> {
        val created: Mono<AppUserDto> =
            // throw error if email exists
            emailExists(req.email)
                .flatMap { exists ->
                    if (exists) return@flatMap Mono.error(UserAlreadyExistsException())
                    else {
                        // if not exists, unwrap access_token:
                        return@flatMap getAuthenticationPrincipal()
                            .map {
                                it.tokenValue
                            }
                            // send user creating request to admin client
                            .flatMap {
                                val accessToken = it
                                keycloakClient.post()
                                    .uri("/api/user")
                                    .header("Authorization", "Bearer $accessToken")
                                    .body(Mono.just(req.toUserRequest()), UserRequest::class.java)
                                    .retrieve()
                                    .bodyToMono(AppUserDto::class.java)
                            }
                    }
                }

        // map to an entity
        val userEntity = req.toEntity()

        // save to local database
        return created.flatMap { user ->
            userEntity.createdDate = LocalDateTime.now()
            userEntity.lastModified = userEntity.createdDate
            userEntity.authId = user.id
            userEntity.isEnabled = true
            appUserRepository.save(userEntity).map { it.toDto() }
        }
    }

    private fun emailExists(email: String): Mono<Boolean> =
        appUserRepository.findByEmail(email)

    override fun findById(id: String): Mono<AppUserDto> {
        return appUserRepository.findByAuthId(UUID.fromString(id))
            .map { it.toDto() }
            .switchIfEmpty(Mono.error(UserNotFoundException()))
    }

    override fun deleteUser(id: String): Mono<Void> =
        checkResourceOwnerThenReturn<Void>(id) {
            getAuthenticationPrincipal()
                .map { jwt ->
                    if (!jwt.claims.containsKey("email")) throw NotLoggedInException()
                    jwt.tokenValue
                }
                .onErrorResume {
                    Mono.error(it)
                }
                .flatMap { token ->
                    keycloakClient.delete()
                        .uri("/api/user/{id}", id)
                        .header("Authorization", "Bearer $token")
                        .retrieve()
                        .bodyToMono(UUID::class.java)
                }
                .flatMap {
                    appUserRepository.findByAuthId(it)
                }
                .flatMap {
                    if (it.isEnabled) {
                        it.isEnabled = false
                        appUserRepository
                            .save(it)
                            .flatMap {
                                Mono.empty<Void>()
                            }
                    } else Mono.error(AccountAlreadyDisabledException())
                }
        }.then()

    override fun updateUser(id: String, req: AppUserProfileRequest): Mono<AppUserDto> =
        checkResourceOwnerThenReturn<AppUser>(id) {
            val entity = req.toEntity()
            entity.authId = it.authId
            entity.id = it.id
            entity.createdDate = it.createdDate
            entity.lastModified = LocalDateTime.now()
            entity.isEnabled = it.isEnabled
            appUserRepository.save(entity)
        }.map { it.toDto() }

    private fun <R> checkResourceOwnerThenReturn(id: String, afterValidation: (AppUser) -> Mono<out R>): Mono<R> =
        appUserRepository.findByAuthId(UUID.fromString(id)).zipWith(getAuthenticationPrincipal())
            .flatMap {
                if (it.t1.authId.toString() == it.t2.claims["sub"].toString())
                // is source owner
                    afterValidation(it.t1)
                else
                // is not resource owner
                    Mono.error(NotResourceOwnerException())
            }

}