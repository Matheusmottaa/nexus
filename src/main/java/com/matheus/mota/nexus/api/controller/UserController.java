package com.matheus.mota.nexus.api.controller;

import com.matheus.mota.nexus.api.dto.user.UpdateUserDto;
import com.matheus.mota.nexus.api.dto.user.UserDetailDto;
import com.matheus.mota.nexus.api.dto.user.UserDto;
import com.matheus.mota.nexus.api.openapi.controller.UserControllerOpenApi;
import com.matheus.mota.nexus.domain.service.UserService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/users")
public class UserController implements UserControllerOpenApi {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<UserDetailDto> findUserById(@PathVariable("id") String userId) {
        return ResponseEntity.ok(userService.findById(userId));
    }

    @Override
    @GetMapping(params = "username")
    public ResponseEntity<UserDto> findUserByUsername(@RequestParam String username) {
        return ResponseEntity.ok(userService.findUserByUsername(username));
    }

    @Override
    @PutMapping("/{id}")
    public ResponseEntity<Void> updateUser(@PathVariable("id") String userId,
                                           @RequestBody @Valid UpdateUserDto userDto) {
        userService.updateUser(userId, userDto);
        return ResponseEntity.noContent().build();
    }

    @Override
    @PatchMapping(value = "/{id}/activation", params = "active")
    public ResponseEntity<Void> updateActivationStatus(@PathVariable("id") String userId,
                                                       @RequestParam boolean active) {
        userService.updateActivationStatus(userId, active);
        return ResponseEntity.noContent().build();
    }

    @Override
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteAccount(@PathVariable("id") String userId) {
        userService.deleteAccountPermanently(userId);
        return ResponseEntity.noContent().build();
    }

    @Override
    @GetMapping(params = "name")
    public ResponseEntity<Page<UserDto>> searchUsersByName(@RequestParam String name,
                                                           Pageable pageable) {
        return ResponseEntity.ok(userService.searchUserByName(name, pageable));
    }
}
