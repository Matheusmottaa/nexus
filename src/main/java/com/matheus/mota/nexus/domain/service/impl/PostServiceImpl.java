package com.matheus.mota.nexus.domain.service.impl;

import com.matheus.mota.nexus.api.dto.post.CreatePostDto;
import com.matheus.mota.nexus.api.dto.post.PostResponseDto;
import com.matheus.mota.nexus.common.exception.*;
import com.matheus.mota.nexus.domain.model.PostEntity;
import com.matheus.mota.nexus.domain.model.PostLikeEntity;
import com.matheus.mota.nexus.domain.model.UserEntity;
import com.matheus.mota.nexus.domain.repository.PostLikeRepository;
import com.matheus.mota.nexus.domain.repository.PostRepository;
import com.matheus.mota.nexus.domain.repository.UserRepository;
import com.matheus.mota.nexus.domain.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;

    private final UserRepository userRepository;

    private final PostLikeRepository postLikeRepository;


    @Override
    @Transactional
    public PostResponseDto createPost(CreatePostDto postDto, UUID userId) {

        UserEntity author = userRepository.findById(userId)
                            .orElseThrow(()-> new UserNotFoundException("Author not found!"));

        if(!StringUtils.hasText(postDto.content())) {
            throw new PostContentEmptyException("Post content cannot be empty!");
        }

        if(postDto.content().length() > 280) {
            throw new PostContentTooLongException("Post content exceeds character limit (280)");
        }

        PostEntity post = new PostEntity();

        post.setAuthor(author);
        post.setContent(postDto.content());
        post.setCommentsCount(0);
        post.setLikesCount(0l);
        post.setViewsCount(0l);

        PostEntity savedPost = postRepository.saveAndFlush(post);

        return new PostResponseDto(savedPost);
    }

    @Override
    @Transactional
    public void deletePost(String postId, UUID requesterId) {
        PostEntity post = postRepository.findById(UUID.fromString(postId))
                .orElseThrow(()-> new PostNotFoundException("Post not found!"));

        if(!post.getAuthor().getId().equals(requesterId)) {
            throw new UnauthorizedPostDeleteException("You do not have permission to delete this post");
        }

       postRepository.delete(post);
    }

    @Override
    public Page<PostResponseDto> listPostByAuthor(UUID authorId, Pageable pageable) {
        UserEntity author = userRepository.findById(authorId)
                .orElseThrow(() -> new UserNotFoundException("No author found!"));

        return postRepository.findAllByAuthor(author, pageable)
                .map(PostResponseDto::new);
    }

    @Override
    @Transactional
    public void likePost(String postId, UUID userId){
        PostEntity post = postRepository.findById(UUID.fromString(postId))
                .orElseThrow(()-> new PostNotFoundException("Post not found!"));

        UserEntity user = userRepository.findById(userId)
                .orElseThrow(()-> new UserNotFoundException("User not found!"));

        boolean alreadyLiked = postLikeRepository.existsByPostAndUser(post, user);

        if(alreadyLiked) {
            throw new PostAlreadyLikedException("Post already liked!");
        }

        PostLikeEntity postLike = new PostLikeEntity();

        postLike.setPost(post);
        postLike.setUser(user);
        postLike.setLikedAt(LocalDateTime.now());

        postLikeRepository.save(postLike);

        post.setLikesCount(post.getLikesCount() + 1);
        postRepository.save(post);
    }

    @Override
    @Transactional
    public void unlikePost(String postId, UUID userId) {

        PostEntity post = postRepository.findById(UUID.fromString(postId))
                .orElseThrow(() -> new PostNotFoundException("Post not found!"));

        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found!"));

        PostLikeEntity postLike = postLikeRepository.findByPostAndUser(post, user)
                .orElseThrow(() -> new PostAlreadyNotLikedException("Post already not liked!"));

        postLikeRepository.delete(postLike);

        post.setLikesCount(Math.max(0, post.getLikesCount() - 1));
        postRepository.save(post);
    }
}
