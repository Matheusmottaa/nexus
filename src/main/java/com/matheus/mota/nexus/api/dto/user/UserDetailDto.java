package com.matheus.mota.nexus.api.dto.user;

import com.matheus.mota.nexus.domain.model.UserEntity;
import com.matheus.mota.nexus.domain.model.enums.ProfileVisibility;

public record UserDetailDto(
        String id,
        String name,
        String lastname,
        String username,
        String email,
        String bio,
        String avatarUrl,
        Boolean active,
        ProfileVisibility profileVisibility
) {

    public UserDetailDto(UserEntity user) {
        this(
                user.getId().toString(),
                user.getName(),
                user.getLastname(),
                user.getUsername(),
                user.getEmail(),
                user.getBio(),
                user.getAvatarUrl(),
                user.getActive(),
                user.getProfileVisibility()
        );
    }

}
