
import java.util.*;
package com.johndoe.javafooddelivery;
public class Order {
    private static int counter = 0;
    private String id;
    private String status;
    private Cart cart;
    private String trackingId;

    public Order(Cart cart) {
        this.id = "ORDER" + ++counter;
        this.status = "PLACED";
        this.cart = cart;
        this.trackingId = UUID.randomUUID().toString();
    }

    public String getId() {
        return id;
    }

    public String getStatus() {
        return status;
    }

    public void updateStatus(String newStatus) {
        this.status = newStatus;
    }

    public String getTrackingId() {
        return trackingId;
    }

    public double getTotalPrice() {
        return cart.getTotalPrice();
    }

    public Map<MenuItem, Integer> getItems() {
        return cart.getItems();
    }
}