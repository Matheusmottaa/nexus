package com.matheus.mota.nexus.domain.repository;

import com.matheus.mota.nexus.domain.model.FollowEntity;
import com.matheus.mota.nexus.domain.model.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface FollowRepository extends JpaRepository<FollowEntity, UUID> {

    boolean existsByFollowerAndFollowing(UserEntity Follower, UserEntity following);

    Optional<FollowEntity> findByFollowerAndFollowing(UserEntity follower, UserEntity following);

    long countByFollower(UserEntity follower);

    long countByFollowing(UserEntity following);

    Page<FollowEntity> findAllByFollower(UserEntity follower, Pageable pageable);

    Page<FollowEntity> findAllByFollowing(UserEntity following, Pageable pageable);

    @Query("""
    FROM FollowEntity f1
    JOIN FollowEntity f2 ON f1.following = f2.following
    WHERE f1.follower.id = :userId1 AND f2.follower.id = :userId2
    """)
    Page<FollowEntity> findMutualFollowings(@Param("userId1") UUID userA,
                                            @Param("userId2") UUID userB,
                                            Pageable pageable);
}
