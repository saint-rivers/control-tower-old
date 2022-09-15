package com.saintrivers.controltower.service.group

import com.saintrivers.controltower.model.dto.GroupDto
import com.saintrivers.controltower.model.request.GroupRequest
import com.saintrivers.controltower.model.request.MemberRequest
import com.saintrivers.controltower.service.user.AppUserRepository
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import java.time.LocalDateTime
import java.util.*

@Service
class GroupServiceImpl(val groupRepository: GroupRepository, val appUserRepository: AppUserRepository) : GroupService {

    override fun create(groupRequest: GroupRequest): Mono<GroupDto> {
        val entity = groupRequest.toEntity()
        entity.createdDate = LocalDateTime.now()
        return groupRepository.save(entity).map { it.toDto() }
    }

    override fun addMember(memberRequest: MemberRequest, requesterId: UUID): Mono<UUID> =
        appUserRepository.findIdByAuthId(requesterId).flatMap { user ->
            groupRepository.memberExistsInGroup(
                groupId = memberRequest.groupId,
                userId = user
            ).flatMap { exists ->
                if (!exists) {
                    appUserRepository.findIdByAuthId(memberRequest.userId)
                        .zipWith(appUserRepository.findIdByAuthId(requesterId))
                        .flatMap {
                            val member = it.t1
                            val addedBy = it.t2

                            groupRepository.addMember(
                                groupId = memberRequest.groupId,
                                userId = member,
                                addedBy = addedBy
                            )
                        }
                        .onErrorResume {
                            Mono.error(it)
                        }
                } else Mono.error(RuntimeException("Member already in group"))
            }
        }


}