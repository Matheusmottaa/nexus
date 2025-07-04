package com.matheus.mota.nexus.api.openapi.controller;


import com.matheus.mota.nexus.api.dto.post.CreatePostDto;
import com.matheus.mota.nexus.api.dto.post.PostResponseDto;
import com.matheus.mota.nexus.api.openapi.schemas.PostResponseSchema;
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
        name = "Posts",
        description = "Operations for creating, listing, liking, and deleting posts."
)
public interface PostControllerOpenApi {

    @Operation(
            summary = "Create a new post",
            description = "Create a new post authored by the authenticated user."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Post created successfully.",
                    content = @Content(schema = @Schema(implementation = PostResponseSchema.class))
            ),
            @ApiResponse(responseCode = "400", description = "Invalid post content.",
                    content = @Content(
                            schema = @Schema(implementation = ProblemDetails.class),
                            examples = @ExampleObject(
                                    value = """
                    {
                      "title": "Invalid Request",
                      "code": 400,
                      "status": "Bad Request",
                      "detail": "Post content must not be empty.",
                      "instance": "/v1/posts"
                    }
                    """)
                    )),
            @ApiResponse(responseCode = "401", description = "User not authenticated.",
                    content = @Content(
                            schema = @Schema(implementation = ProblemDetails.class),
                            examples = @ExampleObject(
                                    value = """
                    {
                      "title": "Authentication Failure",
                      "code": 401,
                      "status": "Unauthorized",
                      "detail": "Authentication token is missing or invalid.",
                      "instance": "/v1/posts"
                    }
                    """)
                    ))
    }) ResponseEntity<PostResponseDto> createPost(
            @Parameter(
                    description = "Data of the post to create.",
                    required = true
            ) CreatePostDto postDto
    );

    @Operation(
            summary = "Delete a post",
            description = "Deletes a post authored by the authenticated user."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Post deleted successfully."),
            @ApiResponse(responseCode = "404", description = "Post not found.",
                    content = @Content(
                            schema = @Schema(implementation = ProblemDetails.class),
                            examples = @ExampleObject(
                                    value = """
                    {
                      "title": "Not Found",
                      "code": 404,
                      "status": "Not Found",
                      "detail": "Post with ID '1f2e3d4c-5678-490a-b123-abcdef123456' was not found.",
                      "instance": "/v1/posts/1f2e3d4c-5678-490a-b123-abcdef123456"
                    }
                    """)
                    )),
            @ApiResponse(responseCode = "403", description = "You do not have permission to delete this post.",
                    content = @Content(
                            schema = @Schema(implementation = ProblemDetails.class),
                            examples = @ExampleObject(
                                    value = """
                    {
                      "title": "Forbidden",
                      "code": 403,
                      "status": "Forbidden",
                      "detail": "You are not allowed to delete this post.",
                      "instance": "/v1/posts/1f2e3d4c-5678-490a-b123-abcdef123456"
                    }
                    """)
                    )),
            @ApiResponse(responseCode = "401", description = "User not authenticated.",
                    content = @Content(
                            schema = @Schema(implementation = ProblemDetails.class),
                            examples = @ExampleObject(
                                    value = """
                    {
                      "title": "Authentication Failure",
                      "code": 401,
                      "status": "Unauthorized",
                      "detail": "Authentication token is missing or invalid.",
                      "instance": "/v1/posts/1f2e3d4c-5678-490a-b123-abcdef123456"
                    }
                    """)
                    ))
    }) ResponseEntity<Void> deletePost(
            @Parameter(
                    name = "postId",
                    description = "UUID of the post to delete.",
                    required = true,
                    example = "1f2e3d4c-5678-490a-b123-abcdef123456"
            ) String postId
    );

    @Operation(
            summary = "List posts authored by the authenticated user",
            description = "Retrieve a paginated list of posts created by the authenticated user."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Posts retrieved successfully."),
            @ApiResponse(responseCode = "401", description = "User not authenticated.",
                    content = @Content(
                            schema = @Schema(implementation = ProblemDetails.class),
                            examples = @ExampleObject(
                                    value = """
                    {
                      "title": "Authentication Failure",
                      "code": 401,
                      "status": "Unauthorized",
                      "detail": "Authentication token is missing or invalid.",
                      "instance": "/v1/posts"
                    }
                    """)
                    ))
    }) ResponseEntity<Page<PostResponseDto>> listPostsByAuthor(
            @Parameter(hidden = true) Pageable pageable
    );

    @Operation(
            summary = "Like a post",
            description = "Register a like for the specified post by the authenticated user."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Post liked successfully."),
            @ApiResponse(responseCode = "404", description = "Post not found.",
                    content = @Content(
                            schema = @Schema(implementation = ProblemDetails.class),
                            examples = @ExampleObject(
                                    value = """
                    {
                      "title": "Not Found",
                      "code": 404,
                      "status": "Not Found",
                      "detail": "Post with ID '1f2e3d4c-5678-490a-b123-abcdef123456' was not found.",
                      "instance": "/v1/posts/1f2e3d4c-5678-490a-b123-abcdef123456/like"
                    }
                    """)
                    )),
            @ApiResponse(responseCode = "409", description = "Post already liked by this user.",
                    content = @Content(
                            schema = @Schema(implementation = ProblemDetails.class),
                            examples = @ExampleObject(
                                    value = """
                    {
                      "title": "Conflict",
                      "code": 409,
                      "status": "Conflict",
                      "detail": "You have already liked this post.",
                      "instance": "/v1/posts/1f2e3d4c-5678-490a-b123-abcdef123456/like"
                    }
                    """)
                    )),
            @ApiResponse(responseCode = "401", description = "User not authenticated.",
                    content = @Content(
                            schema = @Schema(implementation = ProblemDetails.class),
                            examples = @ExampleObject(
                                    value = """
                    {
                      "title": "Authentication Failure",
                      "code": 401,
                      "status": "Unauthorized",
                      "detail": "Authentication token is missing or invalid.",
                      "instance": "/v1/posts/1f2e3d4c-5678-490a-b123-abcdef123456/like"
                    }
                    """)
                    ))
    }) ResponseEntity<Void> likePost(
            @Parameter(
                    name = "postId",
                    description = "UUID of the post to like.",
                    required = true,
                    example = "1f2e3d4c-5678-490a-b123-abcdef123456"
            ) String postId
    );

    @Operation(
            summary = "Unlike a post",
            description = "Remove the like from the specified post by the authenticated user."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Post unliked successfully."),
            @ApiResponse(responseCode = "404", description = "Post not found.",
                    content = @Content(
                            schema = @Schema(implementation = ProblemDetails.class),
                            examples = @ExampleObject(
                                    value = """
                    {
                      "title": "Not Found",
                      "code": 404,
                      "status": "Not Found",
                      "detail": "Post with ID '1f2e3d4c-5678-490a-b123-abcdef123456' was not found.",
                      "instance": "/v1/posts/1f2e3d4c-5678-490a-b123-abcdef123456/unlike"
                    }
                    """)
                    )),
            @ApiResponse(responseCode = "409", description = "Post was not liked by this user.",
                    content = @Content(
                            schema = @Schema(implementation = ProblemDetails.class),
                            examples = @ExampleObject(
                                    value = """
                    {
                      "title": "Conflict",
                      "code": 409,
                      "status": "Conflict",
                      "detail": "This post has not been liked by the user.",
                      "instance": "/v1/posts/1f2e3d4c-5678-490a-b123-abcdef123456/unlike"
                    }
                    """)
                    )),
            @ApiResponse(responseCode = "401", description = "User not authenticated.",
                    content = @Content(
                            schema = @Schema(implementation = ProblemDetails.class),
                            examples = @ExampleObject(
                                    value = """
                    {
                      "title": "Authentication Failure",
                      "code": 401,
                      "status": "Unauthorized",
                      "detail": "Authentication token is missing or invalid.",
                      "instance": "/v1/posts/1f2e3d4c-5678-490a-b123-abcdef123456/unlike"
                    }
                    """)
                    ))
    }) ResponseEntity<Void> unlikePost(
            @Parameter(
                    name = "postId",
                    description = "UUID of the post to unlike.",
                    required = true,
                    example = "1f2e3d4c-5678-490a-b123-abcdef123456"
            ) String postId
    );
}