package com.matheus.mota.nexus.domain.service;

public interface RepostService {
    void repost(String postId, String userId);
    void undoRepost(String postId, String userId);
}
