package com.matheus.mota.nexus.domain.service;

import com.matheus.mota.nexus.api.dto.auth.AuthResponseDto;
import com.matheus.mota.nexus.api.dto.auth.AuthenticationDto;
import com.matheus.mota.nexus.api.dto.auth.RefreshTokenDto;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface AuthenticationService extends UserDetailsService {

    AuthResponseDto login(AuthenticationDto data);

    AuthResponseDto refreshToken(RefreshTokenDto request);

    void logout(RefreshTokenDto request);
}
