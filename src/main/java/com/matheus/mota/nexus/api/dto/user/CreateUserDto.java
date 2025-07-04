package com.matheus.mota.nexus.api.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record CreateUserDto(

        @NotBlank(message = "Required Field")
        @Size(min = 1, max = 25, message = "Field must be between 1 and 25 characters long")
        String name,

        @NotBlank(message = "Required Field")
        @Size(min = 1, max = 25, message = "Field must be between 1 and 25 characters long")
        String lastname,

        @NotBlank(message = "Required Field")
        @Size(min = 1, max = 15, message = "Field must be between 1 and 15 characters long")
        @Pattern(regexp = "^[a-zA-Z0-9._-]+$")
        String username,

        @NotBlank(message = "Required Field")
        @Email(message = "Invalid email provided")
        String email,

        @NotBlank(message = "Required Field")
        String password
) {
}
