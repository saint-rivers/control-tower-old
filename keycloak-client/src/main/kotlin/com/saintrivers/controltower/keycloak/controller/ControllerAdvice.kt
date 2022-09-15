package com.saintrivers.controltower.keycloak.controller

import org.springframework.security.authentication.AuthenticationServiceException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import java.net.NoRouteToHostException

@ControllerAdvice
class ControllerAdvice {


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