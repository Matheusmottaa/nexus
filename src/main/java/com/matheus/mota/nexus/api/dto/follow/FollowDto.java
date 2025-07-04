package com.matheus.mota.nexus.api.dto.follow;

import com.matheus.mota.nexus.domain.model.UserEntity;

public record FollowDto(
        String userId,
        String username,
        String avatarUrl
) {

    public FollowDto(UserEntity user) {
        this(
                user.getId().toString(),
                user.getUsername(),
                user.getAvatarUrl()
        );
    }

}
