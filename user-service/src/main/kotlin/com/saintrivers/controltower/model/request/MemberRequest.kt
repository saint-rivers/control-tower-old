package com.saintrivers.controltower.model.request

import java.util.UUID

data class MemberRequest(
    val groupId: UUID,
    val userId: UUID
)
