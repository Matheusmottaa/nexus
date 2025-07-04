package com.matheus.mota.nexus.api.dto.post;

import jakarta.validation.constraints.Size;
public record CreatePostDto(
        @Size(min = 1, max = 280, message = "Content must be between 1 and 280 characters long.")
        String content
) {
}
