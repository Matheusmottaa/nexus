package com.matheus.mota.nexus.api.controller;

import com.matheus.mota.nexus.api.dto.auth.AuthResponseDto;
import com.matheus.mota.nexus.api.dto.auth.AuthenticationDto;
import com.matheus.mota.nexus.api.dto.auth.RefreshTokenDto;
import com.matheus.mota.nexus.api.dto.user.CreateUserDto;
import com.matheus.mota.nexus.api.openapi.controller.AuthenticationControllerOpenApi;
import com.matheus.mota.nexus.common.exception.UnauthorizedAccessException;
import com.matheus.mota.nexus.domain.service.AuthenticationService;
import com.matheus.mota.nexus.domain.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/v1/auth")
public class AuthenticationController implements AuthenticationControllerOpenApi {

    private AuthenticationService authenticationService;

    private AuthenticationManager authenticationManager;

    private UserService userService;

    public AuthenticationController(AuthenticationService authenticationService,
                                    UserService userService,
                                    AuthenticationManager authenticationManager) {
        this.authenticationService = authenticationService;
        this.userService = userService;
        this.authenticationManager = authenticationManager;
    }

    @Override
    @PostMapping("/register")
    public ResponseEntity<Void> registerUser(@RequestBody @Valid CreateUserDto data) {

        var user = userService.registerUser(data);

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .replacePath("/v1/users")
                .path("/{id}")
                .buildAndExpand(user.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @Override
    @PostMapping("/login")
    public ResponseEntity<AuthResponseDto> login(@RequestBody @Valid AuthenticationDto data) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(data.login(), data.password()));
        } catch(AuthenticationException e) {
            throw new UnauthorizedAccessException("Invalid credentials!");
        }
        var response = authenticationService.login(data);

        return ResponseEntity.ok(response);
    }

    @Override
    @PostMapping("/refresh")
    public ResponseEntity<AuthResponseDto> refreshToken(@RequestBody @Valid RefreshTokenDto request) {
        var response = authenticationService.refreshToken(request);
        return ResponseEntity.ok(response);
    }

    @Override
    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@RequestBody @Valid RefreshTokenDto request) {
        authenticationService.logout(request);
        return ResponseEntity.ok().build();
    }
}
