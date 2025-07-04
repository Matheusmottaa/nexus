package com.matheus.mota.nexus.domain.service;

import com.matheus.mota.nexus.domain.model.RefreshTokenEntity;
import com.matheus.mota.nexus.domain.model.UserEntity;

public interface RefreshTokenService {

    void createRefreshToken(UserEntity user, String token);

    void deleteByToken(String token);

    RefreshTokenEntity validateRefreshToken(String token);
}
