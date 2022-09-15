package com.saintrivers.controltower.model.request

import com.saintrivers.controltower.model.entity.Group

data class GroupRequest(
    val name: String,
    val image: String,
) {
    fun toEntity() = Group(
        name = name,
        image = image
    )
}
