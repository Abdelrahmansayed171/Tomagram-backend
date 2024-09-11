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
@Table(name = "follows")
public class Follows {
    @EmbeddedId
    private FollowsId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("followerUsername")
    @JoinColumn(name="follower_username")
    private User follower;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("followedUsername")
    @JoinColumn(name = "followed_username")
    private User followed;

    @CreationTimestamp
    private LocalDateTime followedAt;

}
