package com.saintrivers.controltower.router.openapi


import com.saintrivers.controltower.handler.GroupHandler
import com.saintrivers.controltower.model.dto.AppUserDto
import com.saintrivers.controltower.model.dto.GroupDto
import com.saintrivers.controltower.model.request.GroupRequest
import com.saintrivers.controltower.model.request.MemberRequest
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
import java.util.*


@RouterOperations(
    value = [
        RouterOperation(
            path = "/api/v1/groups/{id}/users",
            method = [RequestMethod.GET],
            produces = [MediaType.APPLICATION_JSON_VALUE],
            beanClass = GroupHandler::class,
            beanMethod = "findAllGroupMembers",
            operation = Operation(
                operationId = "findAllGroupMembers",
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
            path = "/api/v1/groups",
            method = [RequestMethod.GET],
            produces = [MediaType.APPLICATION_JSON_VALUE],
            beanClass = GroupHandler::class,
            beanMethod = "findGroupsOfLoggedInUser",
            operation = Operation(
                operationId = "findGroupsOfLoggedInUser",
                responses = [ApiResponse(
                    content = [Content(array = ArraySchema(schema = Schema(implementation = AppUserDto::class)))]
                )]
            )
        ),
        RouterOperation(
            path = "/api/v1/groups",
            method = [RequestMethod.POST],
            produces = [MediaType.APPLICATION_JSON_VALUE],
            beanClass = GroupHandler::class,
            beanMethod = "createGroup",
            operation = Operation(
                operationId = "createGroup",
                requestBody = RequestBody(content = [Content(schema = Schema(implementation = GroupRequest::class))]),
                responses = [ApiResponse(
                    content = [Content(schema = Schema(implementation = GroupDto::class))]
                )]
            )
        ),
        RouterOperation(
            path = "/api/v1/groups/members",
            method = [RequestMethod.POST],
            produces = [MediaType.APPLICATION_JSON_VALUE],
            beanClass = GroupHandler::class,
            beanMethod = "addGroupMember",
            operation = Operation(
                operationId = "addGroupMember",
                requestBody = RequestBody(content = [Content(schema = Schema(implementation = MemberRequest::class))]),
                responses = [ApiResponse(
                    content = [Content(schema = Schema(implementation = UUID::class))]
                )]
            )
        )
    ]
)
annotation class GroupRouterOperations()
