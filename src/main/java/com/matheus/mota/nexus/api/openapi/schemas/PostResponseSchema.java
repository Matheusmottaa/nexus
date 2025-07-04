package com.matheus.mota.nexus.api.openapi.schemas;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;
import java.util.UUID;

@Schema(name = "Post", description = "Post response structure")
public record PostResponseSchema (

        @Schema(description = "Post id", example = "d3ba0de5-8442-4cb0-8c2b-1a11fcd4e765")
        UUID id,

        @Schema(description = "Post content", example = "My first post!")
        String content,

        @Schema(description = "Author name", example = "Lionel Messi")
        String authorName,

        @Schema(description = "Creation date", example = "2025-06-12T14:00:00")
        LocalDateTime createdAt
){
}
