package com.matheus.mota.nexus.domain.service.impl;

import com.matheus.mota.nexus.api.dto.user.CreateUserDto;
import com.matheus.mota.nexus.api.dto.user.UpdateUserDto;
import com.matheus.mota.nexus.api.dto.user.UserDetailDto;
import com.matheus.mota.nexus.api.dto.user.UserDto;
import com.matheus.mota.nexus.common.exception.*;
import com.matheus.mota.nexus.domain.model.RoleEntity;
import com.matheus.mota.nexus.domain.model.UserEntity;
import com.matheus.mota.nexus.domain.model.enums.ProfileVisibility;
import com.matheus.mota.nexus.domain.repository.RoleRepository;
import com.matheus.mota.nexus.domain.repository.UserRepository;
import com.matheus.mota.nexus.domain.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    private PasswordEncoder passwordEncoder;

    private RoleRepository roleRepository;

    private final static String ROLE_USER = "ROLE_USER";

    public UserServiceImpl(UserRepository userRepository,
                           PasswordEncoder passwordEncoder,
                           RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }

    @Transactional
    @Override
    public UserEntity registerUser(CreateUserDto userDto) {
        if(!checkUsernameAvailability(userDto.username())) {
            throw new UsernameAlreadyRegisteredException("Username already registered!");
        }

        if(!checkEmailAvailability(userDto.email())) {
            throw new EmailAlreadyRegisteredException("E-mail already registered!");
        }

        String hashedPassword = passwordEncoder.encode(userDto.password());

        RoleEntity role = roleRepository.findByName(ROLE_USER)
                .orElseThrow(() -> new RuntimeException("Role not found!"));

        UserEntity user = new UserEntity(userDto, hashedPassword);
        user.setProfileVisibility(ProfileVisibility.PUBLIC);
        user.setRoles(Set.of(role));

        return userRepository.save(user);
    }


    @Transactional
    @Override
    public void updateUser(String userId, UpdateUserDto userDto) {

        UserEntity user = userRepository.findById(UUID.fromString(userId))
                .orElseThrow(()-> new UserNotFoundException("User not found!"));

        if(userDto.username() != null && !checkUsernameAvailability(userDto.username())) {
            throw new UsernameAlreadyRegisteredException("Username already registered!");
        }

        if(userDto.email() != null && !checkEmailAvailability(userDto.email())) {
            throw new EmailAlreadyRegisteredException("E-mail already registered!");
        }

        user.updateUserInfos(userDto);

        userRepository.save(user);
    }

    @Transactional
    @Override
    public void deactivateUser(String userId) {
        UserEntity user = userRepository.findById(UUID.fromString(userId))
                .orElseThrow(()-> new UserNotFoundException("User account not found!"));

        if(!user.getActive()) {
            throw new UserAlreadyInactiveException("User is already inactive!");
        }

        user.setActive(false);
        userRepository.save(user);
    }

    @Transactional
    @Override
    public void activeUser(String userId) {
        UserEntity user = userRepository.findById(UUID.fromString(userId))
                .orElseThrow(()-> new UserNotFoundException("User account not found!"));

        if(user.getActive()) {
            throw new UserAlreadyActiveException("User is already active!");
        }

        user.setActive(true);
        userRepository.save(user);
    }

    @Override
    public void updateActivationStatus(String userId, boolean activeStatus) {
        if(!activeStatus) {
            deactivateUser(userId);
            return;
        }
        activeUser(userId);
    }

    @Override
    public Page<UserDto> searchUserByName(String name, Pageable pageable) {
        return userRepository.findByNameContainingIgnoreCase(name, pageable)
                .map(UserDto::new);
    }

    @Override
    public UserDto findUserByUsername(String username) {
        UserEntity user = userRepository.findByUsername(username)
                .orElseThrow(()-> new UserNotFoundException("No account found for provided username"));
        return new UserDto(user);
    }


    @Transactional
    @Override
    public void deleteAccountPermanently(String userId) {
        UUID id = UUID.fromString(userId);
        if(!userRepository.existsById(id)) {
            throw new UserNotFoundException("User account not found!");
        }
        userRepository.deleteById(id);
    }

    @Override
    public UserDetailDto findById(String id) {
        UserEntity user = userRepository.findById(UUID.fromString(id))
                .orElseThrow(()-> new UserNotFoundException("User not found!"));
        return new UserDetailDto(user);
    }

    @Override
    public boolean checkUsernameAvailability(String username) {
        return !userRepository.existsByUsername(username);
    }

    @Override
    public boolean checkEmailAvailability(String email) {
        return !userRepository.existsByEmail(email);
    }
}