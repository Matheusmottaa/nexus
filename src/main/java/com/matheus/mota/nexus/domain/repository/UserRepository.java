package com.matheus.mota.nexus.domain.repository;

import com.matheus.mota.nexus.domain.model.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, UUID> {

    Optional<UserEntity> findByEmail(String email);

    Optional<UserEntity> findByUsername(String username);


    @Query("SELECT u FROM UserEntity u WHERE u.username = :login OR u.email = :login")
    Optional<UserEntity> findByLogin(String login);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    Page<UserEntity> findByNameContainingIgnoreCase(String name, Pageable pageable);
}
