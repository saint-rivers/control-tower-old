package com.saintrivers.controltower.service.user

import com.saintrivers.controltower.model.entity.AppUser
import org.springframework.data.r2dbc.repository.Query
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import reactor.core.publisher.Mono
import java.util.UUID

interface AppUserRepository : ReactiveCrudRepository<AppUser, Long> {

    fun findByAuthId(authId: UUID): Mono<AppUser>

    @Query("select id from app_users where auth_id = :authId")
    fun findIdByAuthId(authId: UUID): Mono<Long>
}