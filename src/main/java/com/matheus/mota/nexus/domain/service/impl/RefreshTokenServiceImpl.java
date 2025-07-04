package com.matheus.mota.nexus.domain.service.impl;

import com.matheus.mota.nexus.common.exception.InvalidTokenException;
import com.matheus.mota.nexus.domain.model.RefreshTokenEntity;
import com.matheus.mota.nexus.domain.model.UserEntity;
import com.matheus.mota.nexus.domain.repository.RefreshTokenRepository;
import com.matheus.mota.nexus.domain.service.RefreshTokenService;
import com.matheus.mota.nexus.infrastructure.security.TokenService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class RefreshTokenServiceImpl implements RefreshTokenService {

    private RefreshTokenRepository refreshTokenRepository;

    private TokenService tokenService;

    public RefreshTokenServiceImpl(RefreshTokenRepository refreshTokenRepository,
                                   TokenService tokenService) {
        this.refreshTokenRepository = refreshTokenRepository;
        this.tokenService = tokenService;
    }

    @Override
    @Transactional
    public void createRefreshToken(UserEntity user, String token) {

        LocalDateTime expiresAt = tokenService.extractExpiration(token);
        RefreshTokenEntity refreshToken = new RefreshTokenEntity();
        refreshToken.setToken(token);
        refreshToken.setUser(user);
        refreshToken.setExpiresAt(expiresAt);
        refreshTokenRepository.save(refreshToken);
    }

    @Override
    @Transactional
    public void deleteByToken(String token) {
        if(refreshTokenRepository.existsByToken(token)) {
            refreshTokenRepository.deleteByToken(token);
        }
    }

    @Override
    public RefreshTokenEntity validateRefreshToken(String token) {
        return refreshTokenRepository.findByTokenAndExpiresAtAfter(token, LocalDateTime.now())
                .orElseThrow(()-> new InvalidTokenException("Invalid or expired refresh token"));
    }
}
