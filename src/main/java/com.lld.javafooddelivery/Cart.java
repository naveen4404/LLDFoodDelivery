import java.util.*;
package com.johndoe.javafooddelivery;
public class Cart {
    private Map<MenuItem, Integer> items;

    public Cart() {
        this.items = new HashMap<>();
    }

    public void addItem(MenuItem item, int quantity) {
        items.put(item, items.getOrDefault(item, 0) + quantity);
    }

    public void removeItem(MenuItem item, int quantity) {
        int currentQuantity = items.getOrDefault(item, 0);
        if (currentQuantity > quantity) {
            items.put(item, currentQuantity - quantity);
        } else {
            items.remove(item);
        }
    }

    public void clearCart() {
        items.clear();
    }

    public Map<MenuItem, Integer> getItems() {
        return new HashMap<>(items);
    }

    public double getTotalPrice() {
        return items.entrySet().stream()
                .mapToDouble(e -> e.getKey().getPrice() * e.getValue())
                .sum();
    }
}
