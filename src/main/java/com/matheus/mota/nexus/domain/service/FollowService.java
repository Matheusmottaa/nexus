package com.matheus.mota.nexus.domain.service;

import com.matheus.mota.nexus.api.dto.follow.FollowDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface FollowService {

    void followUser(UUID followerId, String followingId);

    void unfollowUser(UUID followerId, String followingId);

    Page<FollowDto> listByFollower(String followerId, Pageable pageable);

    Page<FollowDto> listByFollowing(String followingId, Pageable pageable);


    Page<FollowDto> findMutualFollowers(UUID userId, String targetId, Pageable pageable);

}
