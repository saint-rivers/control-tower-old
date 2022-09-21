package com.saintrivers.controltower.keycloak.controller

import com.saintrivers.controltower.common.exception.user.UserAlreadyExistsException
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationServiceException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import java.net.NoRouteToHostException

@ControllerAdvice
class ControllerAdvice {

    @ExceptionHandler(value = [Exception::class])
    fun handleUserExistsException(e: Exception) =
        ResponseEntity.badRequest().body(
            mapOf(
                "message" to e.localizedMessage
            )
        )

    @ExceptionHandler(value = [UserAlreadyExistsException::class])
    fun handleUserExistsException(e: UserAlreadyExistsException) =
        ResponseEntity.badRequest().body(
            mapOf(
                "message" to e.localizedMessage
            )
        )

    @ExceptionHandler(value = [NoRouteToHostException::class])
    fun handleKeycloakError(e: NoRouteToHostException) =
        mapOf(
            "error" to "unable to connect to keycloak: no route to host",
            "message" to e.localizedMessage
        )

    @ExceptionHandler(value = [AuthenticationServiceException::class])
    fun handleKeycloakError(e: AuthenticationServiceException) =
        mapOf(
            "error" to "unable to connect to keycloak",
            "message" to e.localizedMessage
        )
}