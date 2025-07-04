package com.matheus.mota.nexus.domain.service;

import com.matheus.mota.nexus.api.dto.user.CreateUserDto;
import com.matheus.mota.nexus.api.dto.user.UpdateUserDto;
import com.matheus.mota.nexus.api.dto.user.UserDetailDto;
import com.matheus.mota.nexus.api.dto.user.UserDto;
import com.matheus.mota.nexus.domain.model.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {

    UserEntity registerUser(CreateUserDto userDto);

    void updateUser(String userId, UpdateUserDto userDto);

    void deactivateUser(String userId);

    void activeUser(String userId);

    void updateActivationStatus(String userId, boolean activeStatus);

    Page<UserDto> searchUserByName(String name, Pageable pageable);

    UserDto findUserByUsername(String username);

    void deleteAccountPermanently(String userId);

    UserDetailDto findById(String userId);

    boolean checkUsernameAvailability(String username);

    boolean checkEmailAvailability(String email);

}
