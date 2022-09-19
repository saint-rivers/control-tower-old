package com.saintrivers.controltower.router.openapi


import com.saintrivers.controltower.handler.AppUserHandler
import com.saintrivers.controltower.model.dto.AppUserDto
import com.saintrivers.controltower.model.request.AppUserRequest
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.enums.Explode
import io.swagger.v3.oas.annotations.enums.ParameterIn
import io.swagger.v3.oas.annotations.enums.ParameterStyle
import io.swagger.v3.oas.annotations.media.ArraySchema
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.parameters.RequestBody
import io.swagger.v3.oas.annotations.responses.ApiResponse
import org.springdoc.core.annotations.RouterOperation
import org.springdoc.core.annotations.RouterOperations
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.RequestMethod


@RouterOperations(
    value = [
        RouterOperation(
            path = "/api/v1/users/{id}",
            method = [RequestMethod.GET],
            produces = [MediaType.APPLICATION_JSON_VALUE],
            beanClass = AppUserHandler::class,
            beanMethod = "findUserById",
            operation = Operation(
                operationId = "findUserById",
                parameters = [
                    Parameter(
                        name = "id",
                        `in` = ParameterIn.PATH,
                        style = ParameterStyle.SIMPLE,
                        explode = Explode.FALSE,
                        required = true,
                    )
                ],
                responses = [ApiResponse(
                    content = [Content(array = ArraySchema(schema = Schema(implementation = AppUserDto::class)))]
                )]
            )
        ),
        RouterOperation(
            path = "/api/v1/users",
            method = [RequestMethod.POST],
            produces = [MediaType.APPLICATION_JSON_VALUE],
            beanClass = AppUserHandler::class,
            beanMethod = "registerUser",
            operation = Operation(
                operationId = "registerUser",
                requestBody = RequestBody(content = [Content(schema = Schema(implementation = AppUserRequest::class))]),
                responses = [ApiResponse(
                    content = [Content(array = ArraySchema(schema = Schema(implementation = AppUserDto::class)))]
                )]
            )
        ),
//        RouterOperation(
//            path = "/api/v1/users/{id}",
//            method = [RequestMethod.PUT],
//            produces = [MediaType.APPLICATION_JSON_VALUE],
//            beanClass = AppUserHandler::class,
//            beanMethod = "updateUser",
//            operation = Operation(
//                operationId = "updateUser",
//                parameters = [
//                    Parameter(
//                        name = "id",
//                        `in` = ParameterIn.PATH,
//                        style = ParameterStyle.SIMPLE,
//                        explode = Explode.FALSE,
//                        required = true,
//                    )
//                ],
//                requestBody = RequestBody(content = [Content(schema = Schema(implementation = AppUserRequest::class))]),
//                responses = [ApiResponse(
//                    content = [Content(schema = Schema(implementation = AppUserDto::class))]
//                )]
//            )
//        ),
        RouterOperation(
            path = "/api/v1/users/{id}",
            method = [RequestMethod.DELETE],
            produces = [MediaType.APPLICATION_JSON_VALUE],
            beanClass = AppUserHandler::class,
            beanMethod = "deleteUser",
            operation = Operation(
                operationId = "deleteUser",
                parameters = [
                    Parameter(
                        name = "id",
                        `in` = ParameterIn.PATH,
                        style = ParameterStyle.SIMPLE,
                        explode = Explode.FALSE,
                        required = true,
                    )
                ],
                responses = [ApiResponse(
                    responseCode = "200"
//                    content = [Content(schema = Schema(implementation = AppUserDto::class))]
                )]
            )
        )
    ]
)
annotation class UserRouterOperations()
