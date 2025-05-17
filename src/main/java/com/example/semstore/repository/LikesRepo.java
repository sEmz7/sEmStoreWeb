package com.example.semstore.repository;

import com.example.semstore.model.Like;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LikesRepo extends JpaRepository<Like, Long> {
    List<Like> findAllByUserId(Long userId);
    Optional<Like> findByUserIdAndPostId(Long userId, Long postId);
}
