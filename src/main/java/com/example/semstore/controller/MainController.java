package com.example.semstore.controller;

import com.example.semstore.config.ConfigLoader;
import com.example.semstore.model.Order;
import com.example.semstore.model.User;
import com.example.semstore.repository.OrderRepo;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
    @Autowired
    private OrderRepo orderRepo;
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

    @GetMapping("/order")
    public String order(HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "login";
        }
        return "order";
    }

    @PostMapping("/api/order")
    @ResponseBody
    public String orderSubmit(@RequestBody Order order, HttpSession session, Model model) {
        User currentUser = (User) session.getAttribute("user");
        order.setUserId(currentUser.getId());
        orderRepo.save(order);

        String message = "📦 Новый заказ!\n\n"
                + "1. ID заказа: " + order.getId() + "\n"
                + "2. Ссылка: " + order.getLink() + "\n"
                + "3. Размер: " + order.getSize() + "\n"
                + "4. Цвет: " + order.getColor() + "\n\n"
                + "От пользователя '" + currentUser.getName() + "' с id: " + currentUser.getId();

        String jsonOrder = gson.toJson(Map.of(
                "chat_id", chatId,
                "text", message
        ));
        sendMessageToTelegram(jsonOrder);
        return "profile";
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
