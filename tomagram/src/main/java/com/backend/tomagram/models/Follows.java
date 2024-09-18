package com.backend.tomagram.models;

import com.backend.tomagram.models.users.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "follows", indexes = {
        @Index(name = "idx_followed_username", columnList = "followed_username")
})
public class Follows {
    @EmbeddedId
    private FollowsId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("followerUsername") // variable name in FollowsId class
    @JoinColumn(name="follower_username") // column name in follows table
    private User follower;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("followedUsername") // variable name in FollowsId class
    @JoinColumn(name = "followed_username") // column name in follows table
    private User followed;

    @CreationTimestamp
    private LocalDateTime followedAt;
}
