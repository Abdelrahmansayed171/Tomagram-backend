package com.backend.tomagram.repository;

import com.backend.tomagram.models.Follows;
import com.backend.tomagram.models.FollowsId;
import com.backend.tomagram.models.users.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FollowsRepository extends JpaRepository<Follows, FollowsId> {
    List<Follows> findByFollower(User follower);
    List<Follows> findByFollowed(User followed);
    boolean existsById(FollowsId id);
    void deleteById(FollowsId id);
}
