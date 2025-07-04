package com.matheus.mota.nexus.api.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UpdateUserDto(

        @Size(min = 1, max = 25, message = "name must be between 1 and 25 characters long")
        String name,

        @Size(min = 1, max = 25, message = "lastname must be between 1 and 25 characters long")
        String lastname,

        @Size(min = 1, max = 15, message = "username must be between 1 and 15 characters long")
        @Pattern(regexp = "^[a-zA-Z0-9._-]+$")
        String username,

        @Email(message = "Invalid e-mail provided")
        String email,

        String password,

        String bio
) {
}
