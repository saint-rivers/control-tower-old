package com.saintrivers.controltower.model.entity

import com.saintrivers.controltower.model.dto.AppUserDto
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.Id
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDateTime
import java.util.*

@Table("app_users")
class AppUser(
    @Id
    @Column("id")
    var id: Long? = null,

    @Column("auth_id")
    var authId: UUID? = null,

    @Column("username")
    var username: String? = null,

    @Column("email")
    var email: String? = null,

    @Column("first_name")
    var firstName: String? = null,

    @Column("last_name")
    var lastName: String? = null,

    @Column("profile_image")
    var profileImage: String? = null,

    @Column("created_date")
    @CreatedDate
    var createdDate: LocalDateTime? = null,

    @Column("last_modified")
    @LastModifiedDate
    var lastModified: LocalDateTime? = null,

    @Column("is_enabled")
    var isEnabled: Boolean = false,
)  {

    fun toDto() = AppUserDto(
        id = authId!!,
        username = username!!,
        email = email!!,
        firstName = firstName!!,
        lastName = lastName!!,
        profileImage = profileImage!!,
        createdDate = createdDate!!,
        lastModified = lastModified!!,
        isEnabled = isEnabled
    )
}