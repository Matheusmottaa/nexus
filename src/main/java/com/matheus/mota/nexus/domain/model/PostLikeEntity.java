package com.matheus.mota.nexus.domain.model;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@Table(name = "tb_post_likes", schema = "public")
@NoArgsConstructor @AllArgsConstructor
public class PostLikeEntity extends Like{

    @ManyToOne
    @JoinColumn(name = "post_id")
    private PostEntity post;
}
