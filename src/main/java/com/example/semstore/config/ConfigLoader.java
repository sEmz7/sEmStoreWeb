package com.example.semstore.config;

import io.github.cdimascio.dotenv.Dotenv;

public class ConfigLoader {
    private static final Dotenv dotenv = Dotenv.load();

    public static String get(String key) {
        return dotenv.get(key);
    }
}
