package com.matheus.mota.nexus.domain.repository;

import com.matheus.mota.nexus.domain.model.RefreshTokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshTokenEntity, UUID> {
    Optional<RefreshTokenEntity> findByToken(String token);
    Optional<RefreshTokenEntity> findByTokenAndExpiresAtAfter(String token, LocalDateTime now);
    boolean existsByToken(String token);
    void deleteByToken(String token);
}
