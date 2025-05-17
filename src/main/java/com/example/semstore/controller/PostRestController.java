package com.example.semstore.controller;

import com.example.semstore.model.Dislike;
import com.example.semstore.model.Like;
import com.example.semstore.model.Post;
import com.example.semstore.model.User;
import com.example.semstore.repository.DislikesRepo;
import com.example.semstore.repository.LikesRepo;
import com.example.semstore.repository.PostRepo;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostRestController {
    private final PostRepo postRepository;
    private final LikesRepo likesRepo;
    private final DislikesRepo dislikesRepo;
    private final SimpMessagingTemplate messagingTemplate;

    @PostMapping
    public ResponseEntity<Post> createPost(@RequestBody Map<String, String> payload, HttpSession session) {
        Post post = new Post();
        post.setContent(payload.get("content"));
        postRepository.save(post);
        messagingTemplate.convertAndSend("/topic/posts", post); // WebSocket push
        return ResponseEntity.ok(post);
    }

    @PostMapping("/{id}/like")
    public ResponseEntity<Post> likePost(@PathVariable Long id, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Optional<Post> optionalPost = postRepository.findById(id);
        if (optionalPost.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Post post = optionalPost.get();

        Optional<Like> existingLike = likesRepo.findByUserIdAndPostId(user.getId(), post.getId());
        Optional<Dislike> existingDislike = dislikesRepo.findByUserIdAndPostId(user.getId(), post.getId());

        if (existingLike.isPresent()) {
            likesRepo.delete(existingLike.get());
            post.setLikes(post.getLikes() - 1);
            postRepository.save(post);
            messagingTemplate.convertAndSend("/topic/posts", post);
            return new ResponseEntity<>(HttpStatus.OK);
        }

        if (existingDislike.isPresent()) {
            dislikesRepo.delete(existingDislike.get());
            post.setDislikes(post.getDislikes() - 1);
        }

        Like like = new Like();
        like.setUserId(user.getId());
        like.setPostId(post.getId());
        likesRepo.save(like);

        post.setLikes(post.getLikes() + 1);
        postRepository.save(post);

        messagingTemplate.convertAndSend("/topic/posts", post);
        return ResponseEntity.ok(post);
    }

    @PostMapping("/{id}/dislike")
    public ResponseEntity<Post> dislikePost(@PathVariable Long id, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Optional<Post> optionalPost = postRepository.findById(id);
        if (optionalPost.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Post post = optionalPost.get();

        Optional<Dislike> existingDislike = dislikesRepo.findByUserIdAndPostId(user.getId(), post.getId());
        Optional<Like> existingLike = likesRepo.findByUserIdAndPostId(user.getId(), post.getId());

        if (existingDislike.isPresent()) {
            dislikesRepo.delete(existingDislike.get());
            post.setDislikes(post.getDislikes() - 1);
            postRepository.save(post);
            messagingTemplate.convertAndSend("/topic/posts", post);
            return new ResponseEntity<>(HttpStatus.OK);
        }

        if (existingLike.isPresent()) {
            likesRepo.delete(existingLike.get());
            post.setLikes(post.getLikes() - 1);
        }

        Dislike dislike = new Dislike();
        dislike.setPostId(post.getId());
        dislike.setUserId(user.getId());
        dislikesRepo.save(dislike);

        post.setDislikes(post.getDislikes() + 1);
        postRepository.save(post);

        messagingTemplate.convertAndSend("/topic/posts", post);
        return ResponseEntity.ok(post);
    }

}

