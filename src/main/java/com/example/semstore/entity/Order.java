package com.example.semstore.entity;

public class Order {
    private String link;
    private String size;
    private String color;

    public Order(String link, String size, String color) {
        this.link = link;
        this.size = size;
        this.color = color;
    }

    public String getLink() {
        return link;
    }

    public String getSize() {
        return size;
    }

    public String getColor() {
        return color;
    }
}
