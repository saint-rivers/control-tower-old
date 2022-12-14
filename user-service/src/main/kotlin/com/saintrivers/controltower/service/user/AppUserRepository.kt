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

    @Query("select exists (select 1 from user_db.public.app_users where email = :email)")
    fun findByEmail(email: String): Mono<Boolean>

    @Query("delete from app_users where auth_id = :authId")
    fun deleteByAuthId(authId: UUID): Mono<Void>
}