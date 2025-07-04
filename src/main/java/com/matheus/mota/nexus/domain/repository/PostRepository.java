package com.matheus.mota.nexus.domain.repository;

import com.matheus.mota.nexus.domain.model.PostEntity;
import com.matheus.mota.nexus.domain.model.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PostRepository extends JpaRepository<PostEntity, UUID> {
    Page<PostEntity> findAllByAuthor(UserEntity author, Pageable pageable);
}
