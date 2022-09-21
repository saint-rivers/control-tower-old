package com.saintrivers.controltower.service.group

import com.saintrivers.controltower.common.exception.user.MemberAlreadyAddedException
import com.saintrivers.controltower.common.exception.user.GroupNotFoundException
import com.saintrivers.controltower.common.exception.user.UserNotFoundException
import com.saintrivers.controltower.model.dto.AppUserDto
import com.saintrivers.controltower.model.dto.GroupDto
import com.saintrivers.controltower.model.request.GroupRequest
import com.saintrivers.controltower.model.request.MemberRequest
import com.saintrivers.controltower.service.user.AppUserRepository
import org.springframework.security.core.context.ReactiveSecurityContextHolder
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.time.LocalDateTime
import java.util.*

@Service
class GroupServiceImpl(val groupRepository: GroupRepository, val appUserRepository: AppUserRepository) : GroupService {

    fun Jwt.getSub(): UUID = UUID.fromString(this.claims["sub"].toString())

    private fun getAuthenticationPrincipal(): Mono<Jwt> =
        ReactiveSecurityContextHolder.getContext()
            .map { it.authentication.principal }
            .cast(Jwt::class.java)

    override fun create(groupRequest: GroupRequest): Mono<GroupDto> {
        val entity = groupRequest.toEntity()
        entity.createdDate = LocalDateTime.now()
        return groupRepository.save(entity).map { it.toDto() }
    }

    override fun addMember(memberRequest: MemberRequest, requesterId: UUID): Mono<UUID> =
        appUserRepository
            // check if provided IDs exist
            .findByAuthId(memberRequest.userId)
            .switchIfEmpty(Mono.error(UserNotFoundException()))
            .flatMap { groupRepository.findById(memberRequest.groupId) }
            .switchIfEmpty(Mono.error(GroupNotFoundException()))
            .flatMap { appUserRepository.findIdByAuthId(requesterId) }
            .switchIfEmpty(Mono.error(UserNotFoundException()))

            // check if the record already exists
            .flatMap { groupRepository.findRecord(groupId = memberRequest.groupId, userId = it) }
            .map { it ?: throw MemberAlreadyAddedException() }

            // insert record after the previous checks
            .then(appUserRepository.findIdByAuthId(memberRequest.userId))
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
            .switchIfEmpty(Mono.error(RuntimeException("unable to add member to group")))


    override fun getMembersByGroupId(groupId: UUID): Flux<AppUserDto> =
        groupRepository.findById(groupId)
            .switchIfEmpty(Mono.error(GroupNotFoundException()))
            .flatMapMany {
                groupRepository.findAllByGroupId(groupId)
            }
            .map { it.toDto() }

    override fun findGroupsOfLoggedInUser(): Flux<GroupDto> {

        val uuid = getAuthenticationPrincipal().map { it.getSub() }
        val emailMono: Mono<String> = getAuthenticationPrincipal().map { it.claims["email"].toString() }

        return emailMono
            .log()
            .flatMap { uuid }
            .flatMap {
                appUserRepository.findIdByAuthId(it)
            }
            .log()
            .flatMapMany {
                groupRepository.findAllGroupsByMemberId(it)
            }
//            .switchIfEmpty(Mono.error(NoContentException()))
            .map {
                it.toDto()
            }
    }

    override fun findGroup(groupId: UUID): Mono<GroupDto> =
        groupRepository.findById(groupId)
            .map { it.toDto() }
            .switchIfEmpty(Mono.error(GroupNotFoundException()))

}