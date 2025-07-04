package com.matheus.mota.nexus.domain.service.impl;

import com.matheus.mota.nexus.api.dto.auth.AuthResponseDto;
import com.matheus.mota.nexus.api.dto.auth.AuthenticationDto;
import com.matheus.mota.nexus.api.dto.auth.RefreshTokenDto;
import com.matheus.mota.nexus.common.exception.UnauthorizedAccessException;
import com.matheus.mota.nexus.common.exception.UserNotFoundException;
import com.matheus.mota.nexus.domain.model.CustomUserDetails;
import com.matheus.mota.nexus.domain.model.RefreshTokenEntity;
import com.matheus.mota.nexus.domain.model.UserEntity;
import com.matheus.mota.nexus.domain.repository.UserRepository;
import com.matheus.mota.nexus.domain.service.AuthenticationService;
import com.matheus.mota.nexus.domain.service.RefreshTokenService;
import com.matheus.mota.nexus.infrastructure.security.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRepository userRepository;

    private final TokenService tokenService;

    private final RefreshTokenService refreshTokenService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = userRepository.findByLogin(username)
                .orElseThrow(()-> new UserNotFoundException("User not found!"));

        return new CustomUserDetails(user);
    }

    @Override
    public AuthResponseDto login(AuthenticationDto data) {
        UserEntity user = userRepository.findByLogin(data.login())
                .orElseThrow(() -> new UnauthorizedAccessException("User not found!"));

        String accessToken = tokenService.generateAccessToken(user);
        String refreshToken = tokenService.generateRefreshToken(user);


        refreshTokenService.createRefreshToken(user, refreshToken);

        return new AuthResponseDto(accessToken, refreshToken);
    }

    @Override
    public AuthResponseDto refreshToken(RefreshTokenDto request) {
        RefreshTokenEntity refreshToken = refreshTokenService.validateRefreshToken(request.refreshToken());
        UserEntity user = refreshToken.getUser();
        String accessToken = tokenService.generateAccessToken(user);

        return new AuthResponseDto(accessToken, request.refreshToken());
    }

    @Override
    public void logout(RefreshTokenDto request) {
        refreshTokenService.deleteByToken(request.refreshToken());
    }
}
