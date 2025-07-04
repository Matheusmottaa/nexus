package com.matheus.mota.nexus.api.dto.user;

import com.matheus.mota.nexus.domain.model.UserEntity;

public record UserDto(
        String id,
        String name,
        String username,
        Boolean active,
        String avatarUrl
) {

    public UserDto(UserEntity user) {
        this (
                user.getId().toString(),
                user.getName(),
                user.getUsername(),
                user.getActive(),
                user.getAvatarUrl()
        );
    }

}
