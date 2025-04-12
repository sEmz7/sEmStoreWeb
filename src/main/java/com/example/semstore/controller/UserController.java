package com.example.semstore.controller;

import com.example.semstore.model.User;
import com.example.semstore.repository.UserRepo;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserController {
    @Autowired
    private UserRepo userRepo;

    @GetMapping("/register")
    public String register() {
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@RequestParam String name, @RequestParam String lastname, @RequestParam String email,
                               @RequestParam String password, Model model) {
        if (userRepo.findByEmail(email) != null ) {
            return "register";
        }
        User newUser = new User();
        newUser.setName(name);
        newUser.setLastname(lastname);
        newUser.setEmail(email);
        newUser.setPassword(password);

        userRepo.save(newUser);

        model.addAttribute("user", newUser);
        return "login.html";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @PostMapping("/login")
    public String loginUser(@RequestParam String email, @RequestParam String password, HttpSession httpSession, Model model) {
        User user = userRepo.findByEmail(email);
        if (user != null && new BCryptPasswordEncoder().matches(password, user.getPassword())) {
            httpSession.setAttribute("user", user);
            return "redirect:/profile";
        } else {
            model.addAttribute("error", "Неверный email или пароль");
            return "login";
        }
    }

    @GetMapping("/profile")
    public String profile(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "login";
        }
        model.addAttribute("user", user);
        return "profile";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "login";
    }
}
