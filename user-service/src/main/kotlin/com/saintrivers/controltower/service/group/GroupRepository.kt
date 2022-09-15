package com.saintrivers.controltower.service.group

import com.saintrivers.controltower.model.entity.Group
import org.springframework.data.r2dbc.repository.Query
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import reactor.core.publisher.Mono
import java.util.UUID

interface GroupRepository: ReactiveCrudRepository<Group, UUID> {

    @Query("insert into group_members(group_id, user_id, added_by, date_added) values (:groupId, :userId, :addedBy, now()) returning group_id")
    fun addMember(groupId: UUID, userId: Long, addedBy: Long): Mono<UUID>

    @Query("select exists (select 1 from group_members where user_id = :userId and group_id = :groupId)")
    fun memberExistsInGroup(groupId: UUID, userId: Long): Mono<Boolean>
}