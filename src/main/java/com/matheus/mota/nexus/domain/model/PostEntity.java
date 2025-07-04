package com.matheus.mota.nexus.domain.model;

import com.matheus.mota.nexus.domain.model.enums.PostStatus;
import com.matheus.mota.nexus.domain.model.enums.PostType;
import com.matheus.mota.nexus.domain.model.enums.PostVisibility;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
@Builder
@Table(name = "tb_posts", schema = "public")
@NoArgsConstructor @AllArgsConstructor
public class PostEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private UserEntity author;

    private String content;

    @Enumerated(EnumType.STRING)
    private PostType postType;

    private Long viewsCount = 0L;

    private Long likesCount = 0L;

    private Integer commentsCount;

    @Enumerated(EnumType.STRING)
    private PostStatus status;

    @Enumerated(EnumType.STRING)
    private PostVisibility visibility;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

}
