package com.matheus.mota.nexus.domain.service;

import com.matheus.mota.nexus.api.dto.post.CreatePostDto;
import com.matheus.mota.nexus.api.dto.post.PostResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface PostService {

    void likePost(String postId, UUID userId);
    void unlikePost(String postId, UUID userId);
    PostResponseDto createPost(CreatePostDto postDto, UUID userId);
    void deletePost(String postId, UUID requesterId);
    Page<PostResponseDto> listPostByAuthor(UUID author, Pageable pageable);

}
