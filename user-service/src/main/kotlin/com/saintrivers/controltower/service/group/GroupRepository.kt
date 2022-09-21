package com.saintrivers.controltower.service.group

import com.saintrivers.controltower.model.entity.AppUser
import com.saintrivers.controltower.model.entity.Group
import org.springframework.data.r2dbc.repository.Query
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.util.UUID

interface GroupRepository : ReactiveCrudRepository<Group, UUID> {

    @Query("insert into group_members(group_id, user_id, added_by, date_added) values (:groupId, :userId, :addedBy, now()) returning group_id")
    fun addMember(groupId: UUID, userId: Long, addedBy: Long): Mono<UUID>

//    @Query("select exists (select 1 from group_members where user_id = :userId and group_id = :groupId)")
//    fun memberExistsInGroup(groupId: UUID, userId: Long): Mono<Boolean>

    @Query("select au.* from group_members gm inner join app_users au on au.id = gm.user_id where gm.group_id = :groupId")
    fun findAllByGroupId(groupId: UUID): Flux<AppUser>

    @Query("select group_id from user_db.public.group_members where group_id = :groupId and user_id = :userId")
    fun findRecord(groupId: UUID, userId: Long): Mono<UUID>

    @Query(
        "select g.* from group_members gm " +
                "inner join groups g " +
                "on g.id = gm.group_id " +
                "where gm.user_id = :userId"
    )
    fun findAllGroupsByMemberId(userId: Long): Flux<Group>


}