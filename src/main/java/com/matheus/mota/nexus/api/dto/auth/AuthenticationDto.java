package com.matheus.mota.nexus.api.dto.auth;

import jakarta.validation.constraints.NotBlank;

public record AuthenticationDto(@NotBlank String login, @NotBlank String password) {
}
