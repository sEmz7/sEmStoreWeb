package com.example.semstore.model;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class User {
    private Long id;

    @NotNull
    @NotBlank
    private String name;

    @NotNull
    @NotBlank
    private String lastname;

    @NotNull
    @NotBlank
    @Email
    private String email;

    @NotNull
    @NotBlank
    private String password;

    public void setPassword(String password) {
        this.password = new BCryptPasswordEncoder().encode(password);
    }
}
