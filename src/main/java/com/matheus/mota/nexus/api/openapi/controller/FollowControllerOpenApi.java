package com.matheus.mota.nexus.api.openapi.controller;

import com.matheus.mota.nexus.api.dto.follow.FollowDto;
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
        name = "Follows",
        description = "Operations related to following and unfollowing users, as well as listing followers and followings."
)
public interface FollowControllerOpenApi {

    @Operation(
            summary = "Follow a user",
            description = "Follow a user by their UUID. The authenticated user will become a follower of the specified user."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User followed successfully."),
            @ApiResponse(responseCode = "400", description = "You cannot follow yourself.",
                    content = @Content(
                            schema = @Schema(implementation = ProblemDetails.class),
                            examples = @ExampleObject(
                                    value = """
                    {
                      "title": "Invalid Request",
                      "code": 400,
                      "status": "Bad Request",
                      "detail": "You cannot follow yourself.",
                      "instance": "/v1/follows/b6a8c0e5-1e75-4b2a-9c24-1c6eec8f5e24/follow"
                    }
                    """)
                    )),
            @ApiResponse(responseCode = "404", description = "The user to be followed was not found.",
                    content = @Content(
                            schema = @Schema(implementation = ProblemDetails.class),
                            examples = @ExampleObject(
                                    value = """
                    {
                      "title": "Not Found",
                      "code": 404,
                      "status": "Not Found",
                      "detail": "User with ID 'b6a8c0e5-1e75-4b2a-9c24-1c6eec8f5e24' was not found.",
                      "instance": "/v1/follows/b6a8c0e5-1e75-4b2a-9c24-1c6eec8f5e24/follow"
                    }
                    """
                            )
                    )),
            @ApiResponse(responseCode = "409", description = "You are already following this user.",
                    content = @Content(
                            schema = @Schema(implementation = ProblemDetails.class),
                            examples = @ExampleObject(
                                    value = """
                    {
                      "title": "Conflict",
                      "code": 409,
                      "status": "Conflict",
                      "detail": "You are already following this user.",
                      "instance": "/v1/follows/b6a8c0e5-1e75-4b2a-9c24-1c6eec8f5e24/follow"
                    }
                    """)
                    ))
    }) ResponseEntity<Void> follow(
            @Parameter(
                    name = "followingId",
                    description = "UUID of the user to follow",
                    required = true,
                    example = "b6a8c0e5-1e75-4b2a-9c24-1c6eec8f5e24"
            ) String followingId);

    @Operation(
            summary = "Unfollow a user",
            description = "Stop following a user by their UUID."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User unfollowed successfully."),
            @ApiResponse(responseCode = "400", description = "You cannot unfollow yourself.",
                    content = @Content(
                            schema = @Schema(implementation = ProblemDetails.class),
                            examples = @ExampleObject(
                                    value = """
                    {
                      "title": "Invalid Request",
                      "code": 400,
                      "status": "Bad Request",
                      "detail": "You cannot unfollow yourself.",
                      "instance": "/v1/follows/b6a8c0e5-1e75-4b2a-9c24-1c6eec8f5e24/unfollow"
                    }
                    """)
                    )),
            @ApiResponse(responseCode = "404", description = "The user to be unfollowed was not found.",
                    content = @Content(
                            schema = @Schema(implementation = ProblemDetails.class),
                            examples = @ExampleObject(
                                    value = """
                    {
                      "title": "Not Found",
                      "code": 404,
                      "status": "Not Found",
                      "detail": "User with ID 'b6a8c0e5-1e75-4b2a-9c24-1c6eec8f5e24' was not found.",
                      "instance": "/v1/follows/b6a8c0e5-1e75-4b2a-9c24-1c6eec8f5e24/unfollow"
                    }
                    """)
                    )),
            @ApiResponse(responseCode = "409", description = "You are not following this user.",
                    content = @Content(
                            schema = @Schema(implementation = ProblemDetails.class),
                            examples = @ExampleObject(
                                    value = """
                    {
                      "title": "Conflict",
                      "code": 409,
                      "status": "Conflict",
                      "detail": "You are not following this user.",
                      "instance": "/v1/follows/b6a8c0e5-1e75-4b2a-9c24-1c6eec8f5e24/unfollow"
                    }
                    """)
                    ))
    }) ResponseEntity<Void> unfollow(
            @Parameter(
                    name = "followingId",
                    description = "UUID of the user to be unfollowed",
                    required = true,
                    example = "b6a8c0e5-1e75-4b2a-9c24-1c6eec8f5e24"
            ) String followingId);

    @Operation(
            summary = "List users that a specific user is following",
            description = "Retrieve a paginated list of users that the specified user is following."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List retrieved successfully."),
            @ApiResponse(responseCode = "404", description = "The specified user was not found.",
                    content = @Content(
                            schema = @Schema(implementation = ProblemDetails.class),
                            examples = @ExampleObject(
                                    value = """
                    {
                      "title": "Not Found",
                      "code": 404,
                      "status": "Not Found",
                      "detail": "User with ID 'b6a8c0e5-1e75-4b2a-9c24-1c6eec8f5e24' was not found.",
                      "instance": "/v1/follows/b6a8c0e5-1e75-4b2a-9c24-1c6eec8f5e24/following"
                    }
                    """)
                    ))
    }) ResponseEntity<Page<FollowDto>> getFollowing(
            @Parameter(
                    name = "userId",
                    description = "UUID of the user whose followings will be listed.",
                    required = true,
                    example = "b6a8c0e5-1e75-4b2a-9c24-1c6eec8f5e24"
            ) String userId,
            @Parameter(hidden = true) Pageable pageable);

    @Operation(
            summary = "List followers of a specific user",
            description = "Retrieve a paginated list of users who follow the specified user."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List retrieved successfully."),
            @ApiResponse(responseCode = "404", description = "The specified user was not found.",
                    content = @Content(
                            schema = @Schema(implementation = ProblemDetails.class),
                            examples = @ExampleObject(
                                    value = """
                    {
                      "title": "Not Found",
                      "code": 404,
                      "status": "Not Found",
                      "detail": "User with ID 'b6a8c0e5-1e75-4b2a-9c24-1c6eec8f5e24' was not found.",
                      "instance": "/v1/follows/b6a8c0e5-1e75-4b2a-9c24-1c6eec8f5e24/followers"
                    }
                    """)))
    }) ResponseEntity<Page<FollowDto>> getFollower(
            @Parameter(
                    name = "userId",
                    description = "UUID of the user whose followers will be listed.",
                    required = true,
                    example = "b6a8c0e5-1e75-4b2a-9c24-1c6eec8f5e24"
            ) String userId,
            @Parameter(hidden = true) Pageable pageable);

    @Operation(
            summary = "List mutual follows with another user",
            description = "Retrieve a list of users that are mutually followed between the authenticated user and the specified user."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Mutual follows retrieved successfully."),
            @ApiResponse(responseCode = "404", description = "The specified user was not found.",
                    content = @Content(
                            schema = @Schema(implementation = ProblemDetails.class),
                            examples = @ExampleObject(
                                    value = """
                    {
                      "title": "Not Found",
                      "code": 404,
                      "status": "Not Found",
                      "detail": "User with ID 'b6a8c0e5-1e75-4b2a-9c24-1c6eec8f5e24' was not found.",
                      "instance": "/v1/follows/b6a8c0e5-1e75-4b2a-9c24-1c6eec8f5e24/mutual"
                    }
                    """)
                    ))
    }) ResponseEntity<Page<FollowDto>> listMutualFollows(
            @Parameter(
                    name = "targetId",
                    description = "UUID of the user to compare mutual follows with.",
                    required = true,
                    example = "b6a8c0e5-1e75-4b2a-9c24-1c6eec8f5e24"
            ) String targetId,
            @Parameter(hidden = true) Pageable pageable);
}