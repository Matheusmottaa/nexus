package com.matheus.mota.nexus.domain.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@Data
@Entity
@Table(name = "tb_follows", schema = "public", uniqueConstraints = {
        @UniqueConstraint(columnNames = {
                "following_id", "follower_id"
        })
})
@NoArgsConstructor @AllArgsConstructor
@EqualsAndHashCode(of = {"id", "following", "follower"})
public class FollowEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "following_id")
    private UserEntity following;

    @ManyToOne
    @JoinColumn(name = "follower_id")
    private UserEntity follower;

    @CreationTimestamp
    private LocalDateTime followedAt;

}
