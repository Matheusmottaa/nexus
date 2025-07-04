package com.matheus.mota.nexus.common.context;

import com.matheus.mota.nexus.common.exception.UnauthorizedAccessException;
import com.matheus.mota.nexus.domain.model.CustomUserDetails;
import com.matheus.mota.nexus.domain.model.UserEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class AuthenticatedUserProvider{

    public UserEntity getAuthenticatedUser() {

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if(!(principal instanceof CustomUserDetails)) {
            throw new UnauthorizedAccessException("User not authenticated!");
        }

        return ((CustomUserDetails) principal).getUser();
    }

    public UUID getAuthenticatedUserId() {
        return getAuthenticatedUser().getId();
    }
}
