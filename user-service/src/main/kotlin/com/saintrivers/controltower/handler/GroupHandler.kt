package com.saintrivers.controltower.handler

import com.saintrivers.controltower.exception.NotLoggedInException
import com.saintrivers.controltower.model.request.GroupRequest
import com.saintrivers.controltower.model.request.MemberRequest
import com.saintrivers.controltower.service.group.GroupService
import org.springframework.security.core.context.ReactiveSecurityContextHolder
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Mono
import java.util.*

@Component
class GroupHandler(
    val groupService: GroupService
) {
    fun getAuthenticationPrincipal(): Mono<Jwt> =
        ReactiveSecurityContextHolder.getContext()
            .map { it.authentication.principal }
            .cast(Jwt::class.java)
            .log()

    fun createGroup(req: ServerRequest): Mono<ServerResponse> =
        req.bodyToMono(GroupRequest::class.java)
            .flatMap {
                groupService.create(it)
            }
            .flatMap {
                ServerResponse.ok().bodyValue(it)
            }

    fun addGroupMember(req: ServerRequest): Mono<ServerResponse> =
        req.bodyToMono(MemberRequest::class.java)
            .zipWith(getAuthenticationPrincipal())
            .flatMap {
                val memberRequest = it.t1
                val sub = it.t2.claims["sub"].toString()
                val requesterId: UUID?

                if (sub == "") return@flatMap Mono.error(NotLoggedInException())
                else requesterId = UUID.fromString(sub)

                groupService.addMember(memberRequest, requesterId)
            }
            .flatMap {
                ServerResponse.ok().body(
                    Mono.just(it), UUID::class.java
                )
            }
            .onErrorResume {
                Mono.just(it.localizedMessage)
                    .flatMap { res ->
                        ServerResponse.badRequest().body(
                            Mono.just(mapOf("message" to res)), Throwable::class.java
                        )
                    }
            }

}

