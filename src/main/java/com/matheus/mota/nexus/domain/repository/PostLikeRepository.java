package com.matheus.mota.nexus.domain.repository;

import com.matheus.mota.nexus.domain.model.PostEntity;
import com.matheus.mota.nexus.domain.model.PostLikeEntity;
import com.matheus.mota.nexus.domain.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PostLikeRepository extends JpaRepository<PostLikeEntity, UUID> {
    boolean existsByPostAndUser(PostEntity post, UserEntity user);
    Optional<PostLikeEntity> findByPostAndUser(PostEntity post, UserEntity user);
}
