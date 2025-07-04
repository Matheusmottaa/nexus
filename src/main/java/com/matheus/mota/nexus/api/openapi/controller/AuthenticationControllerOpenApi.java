package com.matheus.mota.nexus.api.openapi.controller;

import com.matheus.mota.nexus.api.dto.auth.AuthResponseDto;
import com.matheus.mota.nexus.api.dto.auth.AuthenticationDto;
import com.matheus.mota.nexus.api.dto.auth.RefreshTokenDto;
import com.matheus.mota.nexus.api.dto.user.CreateUserDto;
import com.matheus.mota.nexus.common.ProblemDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

@Tag(name = "Authentication", description = "Operations for user registration, login, logout, and token management.")
public interface AuthenticationControllerOpenApi {

    @Operation(
            summary = "Register a new user",
            description = "Create a new user account with the provided information."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User registered successfully."),
            @ApiResponse(responseCode = "400", description = "Invalid request payload.",
                    content = @Content(
                            schema = @Schema(implementation = ProblemDetails.class),
                            examples = @ExampleObject(
                                    value = """
                    {
                      "title": "Invalid Request",
                      "code": 400,
                      "status": "Bad Request",
                      "detail": "The email field must be a valid email address.",
                      "instance": "/v1/auth/register"
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
                      "detail": "An account with this email already exists.",
                      "instance": "/v1/auth/register"
                    }
                    """)
                    ))
    }) ResponseEntity<Void> registerUser(
            @Parameter(
                    description = "Data required to create a user account.",
                    required = true
            ) CreateUserDto data
    );

    @Operation(
            summary = "Authenticate user",
            description = "Authenticate user credentials and return an access token and refresh token."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User authenticated successfully, tokens returned."),
            @ApiResponse(responseCode = "401", description = "Invalid credentials.",
                    content = @Content(
                            schema = @Schema(implementation = ProblemDetails.class),
                            examples = @ExampleObject(
                                    value = """
                    {
                      "title": "Authentication Failure",
                      "code": 401,
                      "status": "Unauthorized",
                      "detail": "The username or password is incorrect.",
                      "instance": "/v1/auth/login"
                    }
                    """)
                    ))
    }) ResponseEntity<AuthResponseDto> login(
            @Parameter(
                    description = "Login credentials (username and password).",
                    required = true
            ) AuthenticationDto data
    );

    @Operation(
            summary = "Refresh access token",
            description = "Obtain a new access token using a valid refresh token."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Token refreshed successfully."),
            @ApiResponse(responseCode = "401", description = "Invalid or expired refresh token.",
                    content = @Content(
                            schema = @Schema(implementation = ProblemDetails.class),
                            examples = @ExampleObject(
                                    value = """
                    {
                      "title": "Authentication Failure",
                      "code": 401,
                      "status": "Unauthorized",
                      "detail": "The refresh token is invalid or has expired.",
                      "instance": "/v1/auth/refresh"
                    }
                    """)
                    ))
    }) ResponseEntity<AuthResponseDto> refreshToken(
            @Parameter(
                    description = "Refresh token to generate a new access token.",
                    required = true
            ) RefreshTokenDto request
    );

    @Operation(
            summary = "Logout user",
            description = "Invalidate the provided refresh token and end the user session."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User logged out successfully."),
            @ApiResponse(responseCode = "401", description = "Invalid or expired refresh token.",
                    content = @Content(
                            schema = @Schema(implementation = ProblemDetails.class),
                            examples = @ExampleObject(
                                    value = """
                    {
                      "title": "Authentication Failure",
                      "code": 401,
                      "status": "Unauthorized",
                      "detail": "The refresh token is invalid or has already been revoked.",
                      "instance": "/v1/auth/logout"
                    }
                    """)
                    ))
    }) ResponseEntity<Void> logout(
            @Parameter(
                    description = "Refresh token to invalidate.",
                    required = true
            ) RefreshTokenDto request
    );
}
