package com.example.semstore.controller;

import com.example.semstore.model.Post;
import com.example.semstore.repository.PostRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/posts")
public class PostRestController {
    @Autowired
    private PostRepo postRepository;
    @Autowired private SimpMessagingTemplate messagingTemplate;

    @PostMapping
    public ResponseEntity<Post> createPost(@RequestBody Map<String, String> payload) {
        Post post = new Post();
        post.setContent(payload.get("content"));
        postRepository.save(post);
        messagingTemplate.convertAndSend("/topic/posts", post); // WebSocket push
        return ResponseEntity.ok(post);
    }

    @PostMapping("/{id}/like")
    public ResponseEntity<Post> likePost(@PathVariable Long id) {
        Post post = postRepository.findById(id).orElseThrow();
        post.setLikes(post.getLikes() + 1);
        postRepository.save(post);
        messagingTemplate.convertAndSend("/topic/posts", post);
        return ResponseEntity.ok(post);
    }

    @PostMapping("/{id}/dislike")
    public ResponseEntity<Post> dislikePost(@PathVariable Long id) {
        Post post = postRepository.findById(id).orElseThrow();
        post.setDislikes(post.getDislikes() + 1);
        postRepository.save(post);
        messagingTemplate.convertAndSend("/topic/posts", post);
        return ResponseEntity.ok(post);
    }
}

