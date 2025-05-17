package com.example.semstore.controller;

import com.example.semstore.model.User;
import com.example.semstore.repository.PostRepo;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PostPageController {
    @Autowired
    PostRepo postRepository;

    @GetMapping("/posts")
    public String postsPage(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "login";
        }
        model.addAttribute("posts", postRepository.findAll(Sort.by(Sort.Direction.DESC, "id")));
        return "posts";
    }
}