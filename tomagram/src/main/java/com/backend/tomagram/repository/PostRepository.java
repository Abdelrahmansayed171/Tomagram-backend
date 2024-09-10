package com.backend.tomagram.repository;

import com.backend.tomagram.models.posts.Post;
import com.backend.tomagram.models.users.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post,Long> {
    List<Post> findByUser(User user);
}
