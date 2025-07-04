package com.matheus.mota.nexus.domain.model;

import com.matheus.mota.nexus.api.dto.user.CreateUserDto;
import com.matheus.mota.nexus.api.dto.user.UpdateUserDto;
import com.matheus.mota.nexus.domain.model.enums.ProfileVisibility;
import com.matheus.mota.nexus.domain.model.enums.UserAccountStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.ColumnTransformer;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.*;

@Data
@Entity
@Table(name = "tb_users", schema = "public")
@NoArgsConstructor @AllArgsConstructor
@ToString(exclude = {"roles", "conversations"})
public class UserEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String name;

    private String lastname;

    private String username;

    private String email;

    private String password;

    private String bio;

    private String avatarUrl;

    private Boolean verified = false;

    private Boolean active = true;

    @Enumerated(EnumType.STRING)
    @ColumnTransformer(write="?::user_status")
    private UserAccountStatus status;

    @Enumerated(EnumType.STRING)
    @ColumnTransformer(write="?::user_profile_visibility")
    private ProfileVisibility profileVisibility;

    private Long followersCount;

    private Long followingCount;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    private LocalDateTime deletedAt;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "tb_user_roles",
               schema = "public",
               joinColumns = @JoinColumn(name = "user_id"),
               inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<RoleEntity> roles = new HashSet<>();


    public UserEntity(CreateUserDto userDto, String hashedPassword) {
        this.name = userDto.name();
        this.lastname = userDto.lastname();
        this.username = userDto.username();
        this.email = userDto.email();
        this.password = hashedPassword;
    }

    public void updateUserInfos(UpdateUserDto userDto) {
        if(userDto.username() != null) {
            this.username = userDto.username();
        }

        if(userDto.email() != null) {
            this.email = userDto.email();
        }

        if(userDto.password() != null) {
            this.password = userDto.password();
        }

        if(userDto.bio() != null) {
            this.bio = userDto.bio();
        }

        if(userDto.name() != null) {
            this.name = userDto.name();
        }

        if(userDto.lastname() != null) {
            this.lastname = userDto.lastname();
        }
    }

}