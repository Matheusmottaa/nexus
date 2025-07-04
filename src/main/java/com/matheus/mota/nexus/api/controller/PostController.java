package com.matheus.mota.nexus.api.controller;

import com.matheus.mota.nexus.api.dto.post.CreatePostDto;
import com.matheus.mota.nexus.api.dto.post.PostResponseDto;
import com.matheus.mota.nexus.api.openapi.controller.PostControllerOpenApi;
import com.matheus.mota.nexus.common.context.AuthenticatedUserProvider;
import com.matheus.mota.nexus.domain.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/v1/posts")
@RequiredArgsConstructor
public class PostController implements PostControllerOpenApi {

    private final PostService postService;

    private final AuthenticatedUserProvider authenticatedUserProvider;

    @Override
    @PostMapping
    public ResponseEntity<PostResponseDto> createPost(@RequestBody CreatePostDto postDto) {
        UUID authorId = authenticatedUserProvider.getAuthenticatedUserId();
        var post = postService.createPost(postDto, authorId);
        return ResponseEntity.status(HttpStatus.CREATED).body(post);
    }

    @Override
    @DeleteMapping("/{postId}")
    public ResponseEntity<Void> deletePost(@PathVariable String postId) {
        UUID authorId = authenticatedUserProvider.getAuthenticatedUserId();
        postService.deletePost(postId, authorId);

        return ResponseEntity.noContent().build();
    }

    @Override
    @GetMapping
    public ResponseEntity<Page<PostResponseDto>> listPostsByAuthor(Pageable pageable) {
        UUID author = authenticatedUserProvider.getAuthenticatedUserId();
        return ResponseEntity.ok(postService.listPostByAuthor(author, pageable));
    }

    @Override
    @PostMapping("/{postId}/like")
    public ResponseEntity<Void> likePost(@PathVariable String postId){
        UUID userId = authenticatedUserProvider.getAuthenticatedUserId();
        postService.likePost(postId, userId);
        return ResponseEntity.ok().build();
    }

    @Override
    @DeleteMapping("/{postId}/unlike")
    public ResponseEntity<Void> unlikePost(@PathVariable String postId) {
        UUID userId = authenticatedUserProvider.getAuthenticatedUserId();
        postService.unlikePost(postId, userId);
        return ResponseEntity.ok().build();
    }
}
