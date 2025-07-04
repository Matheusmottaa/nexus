package com.matheus.mota.nexus.api.dto.post;

import com.matheus.mota.nexus.domain.model.PostEntity;

import java.time.LocalDateTime;
import java.util.UUID;

public record PostResponseDto(
        UUID id,
        String content,
        String authorUsername,
        LocalDateTime createdAt
) {
    public PostResponseDto(PostEntity post) {
        this(post.getId(), post.getContent(), post.getAuthor().getUsername(), post.getCreatedAt());
    }
}
