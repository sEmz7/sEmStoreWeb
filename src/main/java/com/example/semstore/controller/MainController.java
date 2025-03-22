package com.example.semstore.controller;

import com.example.semstore.config.ConfigLoader;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;

@Controller
public class MainController {
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private final String botToken = ConfigLoader.get("TELEGRAM_BOT_TOKEN");
    private final String chatId = ConfigLoader.get("TELEGRAM_CHAT_ID");


    @GetMapping("/index")
    public String index() {
        return "index";
    }

    @GetMapping("/about")
    public String about() {
        return "about";
    }

    @GetMapping("/contact")
    public String contact() {
        return "contact";
    }

    @GetMapping("/register")
    public String register() {
        return "register";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/order")
    public String order() {
        return "order";
    }

    @PostMapping("/api/order")
    @ResponseBody
    public void orderSubmit(@RequestBody Map<String, String> orderData) {
        String message = "📦 Новый заказ!\n\n"
                + "1. Ссылка:   " + orderData.get("productLink") + "\n"
                + "2. Размер:   " + orderData.get("size") + "\n"
                + "3. Цвет:   " + orderData.get("color");

        String jsonOrder = gson.toJson(Map.of(
                "chat_id", chatId,
                "text", message
        ));
        sendMessageToTelegram(jsonOrder);
    }

    private void sendMessageToTelegram(String jsonOrder) {
        String url = "https://api.telegram.org/bot" + botToken + "/sendMessage";

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonOrder))
                .build();
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            System.out.println("Не получилось отправить запрос");
        }
    }
}
