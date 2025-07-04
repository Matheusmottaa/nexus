package com.matheus.mota.nexus.api.openapi.controller;

import com.matheus.mota.nexus.api.dto.user.UpdateUserDto;
import com.matheus.mota.nexus.api.dto.user.UserDetailDto;
import com.matheus.mota.nexus.api.dto.user.UserDto;
import com.matheus.mota.nexus.common.ProblemDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

@Tag(
        name = "Users",
        description = "Operations for managing user accounts, profiles, and searching users."
)
public interface UserControllerOpenApi {


    @Operation(
            summary = "Find user by ID",
            description = "Retrieve detailed information of a user by their unique identifier."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User retrieved successfully."),
            @ApiResponse(responseCode = "404", description = "User not found.",
                    content = @Content(
                            schema = @Schema(implementation = ProblemDetails.class),
                            examples = @ExampleObject(
                                    value = """
                    {
                      "title": "Not Found",
                      "code": 404,
                      "status": "Not Found",
                      "detail": "User with ID 'a1b2c3d4-5678-490a-b123-abcdef123456' was not found.",
                      "instance": "/v1/users/a1b2c3d4-5678-490a-b123-abcdef123456"
                    }
                    """)
                    ))
    }) ResponseEntity<UserDetailDto> findUserById(
            @Parameter(
                    name = "id",
                    description = "UUID of the user.",
                    required = true,
                    example = "a1b2c3d4-5678-490a-b123-abcdef123456"
            ) String userId
    );

    @Operation(
            summary = "Find user by username",
            description = "Retrieve basic information of a user by their username."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User retrieved successfully."),
            @ApiResponse(responseCode = "404", description = "No user found with the provided username.",
                    content = @Content(
                            schema = @Schema(implementation = ProblemDetails.class),
                            examples = @ExampleObject(
                                    value = """
                    {
                      "title": "Not Found",
                      "code": 404,
                      "status": "Not Found",
                      "detail": "No user found with username 'johndoe'.",
                      "instance": "/v1/users?username=johndoe"
                    }
                    """)
                    ))
    }) ResponseEntity<UserDto> findUserByUsername(
            @Parameter(
                    name = "username",
                    description = "Username of the user.",
                    required = true,
                    example = "johndoe"
            ) String username
    );

    @Operation(
            summary = "Update user information",
            description = "Update the profile details of a user account."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "User updated successfully."),
            @ApiResponse(responseCode = "400", description = "Invalid request payload.",
                    content = @Content(
                            schema = @Schema(implementation = ProblemDetails.class),
                            examples = @ExampleObject(
                                    value = """
                    {
                      "title": "Invalid Request",
                      "code": 400,
                      "status": "Bad Request",
                      "detail": "Email format is invalid.",
                      "instance": "/v1/users/a1b2c3d4-5678-490a-b123-abcdef123456"
                    }
                    """)
                    )),
            @ApiResponse(responseCode = "403", description = "You do not have permission to update this user.",
                    content = @Content(
                            schema = @Schema(implementation = ProblemDetails.class),
                            examples = @ExampleObject(
                                    value = """
                    {
                      "title": "Forbidden",
                      "code": 403,
                      "status": "Forbidden",
                      "detail": "You are not allowed to update this user.",
                      "instance": "/v1/users/a1b2c3d4-5678-490a-b123-abcdef123456"
                    }
                    """)
                    )),
            @ApiResponse(responseCode = "404", description = "User not found.",
                    content = @Content(
                            schema = @Schema(implementation = ProblemDetails.class),
                            examples = @ExampleObject(
                                    value = """
                    {
                      "title": "Not Found",
                      "code": 404,
                      "status": "Not Found",
                      "detail": "User with ID 'a1b2c3d4-5678-490a-b123-abcdef123456' was not found.",
                      "instance": "/v1/users/a1b2c3d4-5678-490a-b123-abcdef123456"
                    }
                    """)
                    )),
            @ApiResponse(responseCode = "409", description = "Username or email already registered.",
                    content = @Content(
                            schema = @Schema(implementation = ProblemDetails.class),
                            examples = @ExampleObject(
                                    value = """
                    {
                      "title": "Conflict",
                      "code": 409,
                      "status": "Conflict",
                      "detail": "This email is already associated with another account.",
                      "instance": "/v1/users/a1b2c3d4-5678-490a-b123-abcdef123456"
                    }
                    """)
                    ))
    }) ResponseEntity<Void> updateUser(
            @Parameter(
                    name = "id",
                    description = "UUID of the user.",
                    required = true,
                    example = "a1b2c3d4-5678-490a-b123-abcdef123456"
            ) String userId,
            @Parameter(
                    description = "User information to update."
            ) UpdateUserDto userDto
    );

    @Operation(
            summary = "Update user activation status",
            description = "Activate or deactivate a user account."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Activation status updated successfully."),
            @ApiResponse(responseCode = "404", description = "User not found.",
                    content = @Content(
                            schema = @Schema(implementation = ProblemDetails.class),
                            examples = @ExampleObject(
                                    value = """
                    {
                      "title": "Not Found",
                      "code": 404,
                      "status": "Not Found",
                      "detail": "User with ID 'a1b2c3d4-5678-490a-b123-abcdef123456' was not found.",
                      "instance": "/v1/users/a1b2c3d4-5678-490a-b123-abcdef123456/activation"
                    }
                    """)
                    )),
            @ApiResponse(responseCode = "409", description = "Account already in the requested status.",
                    content = @Content(
                            schema = @Schema(implementation = ProblemDetails.class),
                            examples = @ExampleObject(
                                    value = """
                    {
                      "title": "Conflict",
                      "code": 409,
                      "status": "Conflict",
                      "detail": "The account is already active.",
                      "instance": "/v1/users/a1b2c3d4-5678-490a-b123-abcdef123456/activation"
                    }
                    """)
                    ))
    }) ResponseEntity<Void> updateActivationStatus(
            @Parameter(
                    name = "id",
                    description = "UUID of the user.",
                    required = true,
                    example = "a1b2c3d4-5678-490a-b123-abcdef123456"
            ) String userId,
            @Parameter(
                    name = "active",
                    description = "Set to true to activate the account, or false to deactivate.",
                    required = true,
                    example = "true"
            ) boolean active
    );

    @Operation(
            summary = "Delete user account",
            description = "Permanently delete a user account by its ID."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "User account deleted successfully."),
            @ApiResponse(responseCode = "403", description = "You do not have permission to delete this account.",
                    content = @Content(
                            schema = @Schema(implementation = ProblemDetails.class),
                            examples = @ExampleObject(
                                    value = """
                    {
                      "title": "Forbidden",
                      "code": 403,
                      "status": "Forbidden",
                      "detail": "You are not allowed to delete this user.",
                      "instance": "/v1/users/a1b2c3d4-5678-490a-b123-abcdef123456"
                    }
                    """)
                    )),
            @ApiResponse(responseCode = "404", description = "User not found.",
                    content = @Content(
                            schema = @Schema(implementation = ProblemDetails.class),
                            examples = @ExampleObject(
                                    value = """
                    {
                      "title": "Not Found",
                      "code": 404,
                      "status": "Not Found",
                      "detail": "User with ID 'a1b2c3d4-5678-490a-b123-abcdef123456' was not found.",
                      "instance": "/v1/users/a1b2c3d4-5678-490a-b123-abcdef123456"
                    }
                    """)
                    ))
    }) ResponseEntity<Void> deleteAccount(
            @Parameter(
                    name = "id",
                    description = "UUID of the user.",
                    required = true,
                    example = "a1b2c3d4-5678-490a-b123-abcdef123456"
            ) String userId
    );

    @Operation(
            summary = "Search users by name",
            description = "Retrieve a paginated list of users whose names contain the provided query string."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Users retrieved successfully.")
    }) ResponseEntity<Page<UserDto>> searchUsersByName(
            @Parameter(
                    name = "name",
                    description = "Substring to search in user names.",
                    required = true,
                    example = "john"
            ) String name,
            @Parameter(hidden = true) Pageable pageable
    );

}

