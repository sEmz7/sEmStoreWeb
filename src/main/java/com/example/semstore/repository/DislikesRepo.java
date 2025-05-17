package com.example.semstore.repository;

import com.example.semstore.model.Dislike;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DislikesRepo extends JpaRepository<Dislike, Long> {
    List<Dislike> findAllByUserId(Long userId);
    Optional<Dislike> findByUserIdAndPostId(Long userId, Long postId);
}
