package com.saintrivers.controltower.service.group

import com.saintrivers.controltower.model.dto.AppUserDto
import com.saintrivers.controltower.model.dto.GroupDto
import com.saintrivers.controltower.model.request.GroupRequest
import com.saintrivers.controltower.model.request.MemberRequest
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.util.UUID

interface GroupService {

    fun create(groupRequest: GroupRequest): Mono<GroupDto>
    fun addMember(memberRequest: MemberRequest, requesterId: UUID): Mono<UUID>
    fun getMembersByGroupId(groupId: UUID): Flux<AppUserDto>
}