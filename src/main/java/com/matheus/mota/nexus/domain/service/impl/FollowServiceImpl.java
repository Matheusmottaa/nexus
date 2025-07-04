package com.matheus.mota.nexus.domain.service.impl;

import com.matheus.mota.nexus.api.dto.follow.FollowDto;
import com.matheus.mota.nexus.common.exception.*;
import com.matheus.mota.nexus.domain.model.FollowEntity;
import com.matheus.mota.nexus.domain.model.UserEntity;
import com.matheus.mota.nexus.domain.repository.FollowRepository;
import com.matheus.mota.nexus.domain.repository.UserRepository;
import com.matheus.mota.nexus.domain.service.FollowService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class FollowServiceImpl implements FollowService {

    private UserRepository userRepository;

    private FollowRepository followRepository;

    public FollowServiceImpl(FollowRepository followRepository,
                             UserRepository userRepository) {
        this.followRepository = followRepository;
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public void followUser(UUID followerId, String followingId) {

        UUID followingUUID = UUID.fromString(followingId);

        if(followerId.equals(followingUUID)) {
            throw new SelfFollowNotAllowedException("You can't follow yourself!");
        }

        UserEntity follower = userRepository.findById(followerId)
                .orElseThrow(() -> new UserNotFoundException("Follower not found!"));

        UserEntity following = userRepository.findById(followingUUID)
                .orElseThrow(()-> new UserNotFoundException("Following not found!"));

        if(followRepository.existsByFollowerAndFollowing(follower, following)) {
            throw new AlreadyFollowingUserException("You already follow this user!");
        }

        FollowEntity follow = FollowEntity.builder()
                .follower(follower)
                .following(following)
                .followedAt(LocalDateTime.now())
                .build();

        followRepository.save(follow);
    }


    @Override
    @Transactional
    public void unfollowUser(UUID followerId, String followingId) {
        UUID followingUUID = UUID.fromString(followingId);

        if(followerId.equals(followingUUID)) {
            throw new SelfUnFollowNotAllowedException("You can't unfollow yourself!");
        }

        UserEntity follower = userRepository.findById(followerId)
                .orElseThrow(() -> new UserNotFoundException("Follower not found!"));

        UserEntity following = userRepository.findById(followingUUID)
                .orElseThrow(()-> new UserNotFoundException("Following not found!"));

        FollowEntity follow = followRepository.findByFollowerAndFollowing(follower, following)
                .orElseThrow(() -> new NotFollowingUserException("You already don't follow this user!"));

        followRepository.delete(follow);
    }

    @Override
    public Page<FollowDto> listByFollower(String followerId, Pageable pageable) {

        UserEntity follower = userRepository.findById(UUID.fromString(followerId))
                .orElseThrow(()-> new UserNotFoundException("Follower not found!"));

        return followRepository.findAllByFollowing(follower, pageable)
                .map(f -> new FollowDto(f.getFollower()));
    }

    @Override
    public Page<FollowDto> listByFollowing(String followingId, Pageable pageable) {
        UserEntity following = userRepository.findById(UUID.fromString(followingId))
                .orElseThrow(()-> new UserNotFoundException("Following not found!"));

        return followRepository.findAllByFollower(following, pageable)
                .map(f -> new FollowDto(f.getFollowing()));
    }

    @Override
    public Page<FollowDto> findMutualFollowers(UUID userId, String targetId, Pageable pageable) {
        return followRepository.findMutualFollowings(userId, UUID.fromString(targetId), pageable)
                .map(follow -> new FollowDto(follow.getFollowing()));
    }
}
