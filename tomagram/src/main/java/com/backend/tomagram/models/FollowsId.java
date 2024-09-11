package com.backend.tomagram.models;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FollowsId implements Serializable {
    private String followerUsername;
    private String followedUsername;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true; // if 2 objects are the same return true;
        if (o == null || getClass() != o.getClass()) return false; // if 2 objects from different classes
        FollowsId followsId = (FollowsId) o; // parse object into FollowsId instance
        // set normal attribute equality check
        return Objects.equals(followerUsername, followsId.followerUsername) && Objects.equals(followedUsername, followsId.followedUsername);
    }

    @Override
    public int hashCode() {
        return Objects.hash(followerUsername, followedUsername);
    }
}
