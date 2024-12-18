import java.util.*;
package com.johndoe.javafooddelivery;
public class Restaurant {
    private String id;
    private String name;
    private Map<String, MenuItem> menuItems;

    public Restaurant(String id, String name) {
        this.id = id;
        this.name = name;
        this.menuItems = new HashMap<>();
    }

    public void addMenuItem(MenuItem item) {
        menuItems.put(item.getName(), item);
    }

    public MenuItem getMenuItem(String name) {
        return menuItems.get(name);
    }

    public Collection<MenuItem> getMenuItems() {
        return menuItems.values();
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
