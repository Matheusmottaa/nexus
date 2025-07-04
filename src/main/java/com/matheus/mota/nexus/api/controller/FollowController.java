package com.matheus.mota.nexus.api.controller;

import com.matheus.mota.nexus.api.dto.follow.FollowDto;
import com.matheus.mota.nexus.api.openapi.controller.FollowControllerOpenApi;
import com.matheus.mota.nexus.common.context.AuthenticatedUserProvider;
import com.matheus.mota.nexus.domain.service.FollowService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/v1/follows")
@RequiredArgsConstructor
public class FollowController implements FollowControllerOpenApi {

    private final FollowService followService;

    private final AuthenticatedUserProvider authenticatedUserProvider;

    @Override
    @PostMapping("/{followingId}/follow")
    public ResponseEntity<Void> follow(@PathVariable String followingId) {
        UUID followerId = authenticatedUserProvider.getAuthenticatedUserId();
        followService.followUser(followerId, followingId);
        return ResponseEntity.ok().build();
    }

    @Override
    @DeleteMapping("/{followingId}/unfollow")
    public ResponseEntity<Void> unfollow(@PathVariable("followingId") String targetId) {
        UUID followerId = authenticatedUserProvider.getAuthenticatedUserId();
        followService.unfollowUser(followerId, targetId);
        return ResponseEntity.ok().build();
    }

    @Override
    @GetMapping("/{userId}/following")
    public ResponseEntity<Page<FollowDto>> getFollowing(@PathVariable String userId, Pageable pageable) {
        return ResponseEntity.ok(followService.listByFollowing(userId, pageable));
    }

    @Override
    @GetMapping("/{userId}/followers")
    public ResponseEntity<Page<FollowDto>> getFollower(@PathVariable String userId, Pageable pageable) {
        return ResponseEntity.ok(followService.listByFollower(userId, pageable));
    }

    @Override
    @GetMapping("/{targetId}/mutual")
    public ResponseEntity<Page<FollowDto>> listMutualFollows(@PathVariable String targetId,
                                                             Pageable pageable) {
        UUID userId = authenticatedUserProvider.getAuthenticatedUserId();
        return ResponseEntity.ok(followService.findMutualFollowers(userId, targetId, pageable));
    }

}
