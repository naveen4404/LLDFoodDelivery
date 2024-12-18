import java.util.*;
package com.johndoe.javafooddelivery;
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<Restaurant> restaurants = setupRestaurants();
        Restaurant selectedRestaurant = selectRestaurant(scanner, restaurants);
        if (selectedRestaurant == null) {
            System.out.println("No restaurant selected. Exiting.");
            scanner.close();
            return;
        }
        Cart cart = new Cart();
        Order order = null;

        while (true) {
            displayMenu(selectedRestaurant);
            int choice = getChoice(scanner);
            
            if (choice == -1) continue;  // Invalid input, try again
            
            if (choice == 1) {
                viewCart(cart);
            } else if (choice == 2) {
                addItemToCart(scanner, selectedRestaurant, cart);
            } else if (choice == 3) {
                removeItemFromCart(scanner, selectedRestaurant, cart);
            } else if (choice == 4) {
                cart.clearCart();
                System.out.println("Cart cleared. Do you want to continue with the same restaurant? (yes/no)");
                String continueSame = scanner.next().toLowerCase();
                if (!continueSame.equals("yes")) {
                    selectedRestaurant = selectRestaurant(scanner, restaurants);
                    if (selectedRestaurant == null) {
                        System.out.println("No restaurant selected. Exiting.");
                        scanner.close();
                        return;
                    }
                }
            } else if (choice == 5 && cart.getItems().size() > 0) {
                order = proceedWithOrder(scanner, cart);
                break;
            } else if (choice == 5 && cart.getItems().size() == 0) {
                System.out.println("Please add items to the cart before proceeding with the order.");
            } else {
                System.out.println("Invalid option, please try again.");
            }
        }

        if (order != null) {
            System.out.println("Your order has been placed. Tracking ID: " + order.getTrackingId());
            trackOrder(scanner, order);
        }

        scanner.close();
    }

    private static List<Restaurant> setupRestaurants() {
        List<Restaurant> restaurants = new ArrayList<>();
        Restaurant r1 = new Restaurant("R1", "Burger Joint");
        r1.addMenuItem(new MenuItem("Burger", 5.0));
        r1.addMenuItem(new MenuItem("Fries", 2.0));
        r1.addMenuItem(new MenuItem("Soda", 1.5));
        restaurants.add(r1);

        Restaurant r2 = new Restaurant("R2", "Pizza Place");
        r2.addMenuItem(new MenuItem("Pizza", 10.0));
        r2.addMenuItem(new MenuItem("Salad", 3.0));
        restaurants.add(r2);

        return restaurants;
    }

    private static Restaurant selectRestaurant(Scanner scanner, List<Restaurant> restaurants) {
        System.out.println("Available Restaurants:");
        for (int i = 0; i < restaurants.size(); i++) {
            System.out.println((i + 1) + ". " + restaurants.get(i).getName());
        }
        System.out.print("Select a restaurant by number: ");
        int choice = getValidIntInput(scanner, 1, restaurants.size());
        if (choice == -1) return null;
        return restaurants.get(choice - 1);
    }

    private static void displayMenu(Restaurant restaurant) {
        System.out.println("\nMenu for " + restaurant.getName() + ":");
        for (MenuItem item : restaurant.getMenuItems()) {
            System.out.println(item.getName() + " - $" + item.getPrice());
        }
        System.out.println("\nCart Options:");
        System.out.println("1. View Cart");
        System.out.println("2. Add Item");
        System.out.println("3. Remove Item");
        System.out.println("4. Clear Cart");
        System.out.println("5. Proceed with Order");
    }

    private static int getChoice(Scanner scanner) {
        System.out.print("Choose an option: ");
        return getValidIntInput(scanner, 1, 5);
    }

    private static int getValidIntInput(Scanner scanner, int min, int max) {
        try {
            int choice = scanner.nextInt();
            if (choice >= min && choice <= max) {
                return choice;
            } else {
                System.out.println("Invalid choice. Please enter a number between " + min + " and " + max + ".");
            }
        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Please enter a number.");
        }
        scanner.nextLine();
        return -1;
    }

    private static void viewCart(Cart cart) {
        Map<MenuItem, Integer> items = cart.getItems();
        if (items.isEmpty()) {
            System.out.println("Your cart is empty.");
        } else {
            System.out.println("Items in your cart:");
            double totalPrice = 0;
            for (Map.Entry<MenuItem, Integer> entry : items.entrySet()) {
                MenuItem item = entry.getKey();
                int quantity = entry.getValue();
                double itemTotal = item.getPrice() * quantity;
                System.out.println(item.getName() + " x " + quantity + " - $" + itemTotal);
                totalPrice += itemTotal;
            }
            System.out.println("Total Price: $" + totalPrice);
        }
    }

    private static void addItemToCart(Scanner scanner, Restaurant restaurant, Cart cart) {
        System.out.print("Enter item name: ");
        String itemName = scanner.next();
        MenuItem item = restaurant.getMenuItem(itemName);
        if (item != null) {
            System.out.print("Enter quantity: ");
            int quantity = getValidIntInput(scanner, 1, Integer.MAX_VALUE);
            if (quantity != -1) {
                cart.addItem(item, quantity);
                System.out.println(itemName + " added to cart.");
            }
        } else {
            System.out.println("Item not found in menu.");
        }
    }

    private static void removeItemFromCart(Scanner scanner, Restaurant restaurant, Cart cart) {
        System.out.print("Enter item name to remove: ");
        String itemName = scanner.next();
        MenuItem item = restaurant.getMenuItem(itemName);
        if (item != null && cart.getItems().containsKey(item)) {
            int currentQuantity = cart.getItems().get(item);
            if (currentQuantity > 1) {
                System.out.print("Enter quantity to remove (up to " + currentQuantity + "): ");
                int quantity = getValidIntInput(scanner, 1, currentQuantity);
                if (quantity != -1) {
                    cart.removeItem(item, quantity);
                    System.out.println(quantity + " " + itemName + "(s) removed from cart.");
                }
            } else {
                cart.removeItem(item, 1);
                System.out.println(itemName + " removed from cart.");
            }
        } else {
            System.out.println("Item not found in cart or menu.");
        }
    }

    private static void trackOrder(Scanner scanner, Order order) {
        int count = 0;
        while (count < 2) {
            System.out.println("Track your order (Enter anything to check status):");
            scanner.next();
            if (count == 0) {
                order.updateStatus("OUT_FOR_DELIVERY");
                System.out.println("Order status: " + order.getStatus());
            } else {
                order.updateStatus("DELIVERED");
                System.out.println("Order status: " + order.getStatus() + " - Delivered Successfully!");
            }
            count++;
        }
    }

    private static Order proceedWithOrder(Scanner scanner, Cart cart) {
        System.out.println("Choose payment method:");
        System.out.println("1. UPI\n2. Card\n3. Cash on Delivery\n4. Cancel the Order");
        int paymentChoice = getValidIntInput(scanner, 1, 4);
        
        if (paymentChoice == -1) return null; // Invalid input, return null

        if (paymentChoice == 4) {
            System.out.println("Order Cancelled. Returning to restaurant selection.");
            return null;
        } else {
            Payment payment = getPaymentMethod(paymentChoice);
            Order order = new Order(cart);
            payment.processPayment(order.getTotalPrice());
            return order;
        }
    }

    private static Payment getPaymentMethod(int choice) {
        switch (choice) {
            case 1: return new UPI();
            case 2: return new Card();
            case 3: return new CashOnDelivery();
            default: throw new IllegalArgumentException("Invalid payment choice");
        }
    }
}