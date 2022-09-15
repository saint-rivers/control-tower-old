package com.saintrivers.controltower.service

import com.saintrivers.controltower.model.entity.AppUser
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface AppUserRepository : ReactiveCrudRepository<AppUser, UUID> {
}