package com.matheus.mota.nexus.api.dto.auth;

import jakarta.validation.constraints.NotEmpty;

public record RefreshTokenDto(@NotEmpty String refreshToken) {
}
