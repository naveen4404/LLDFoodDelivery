package com.johndoe.javafooddelivery;
public class UPI implements Payment {

    public void processPayment(double amount) {
        System.out.println("Processing UPI payment of $" + amount);
    }

    public String getPaymentType() {
        return "UPI";
    }
}
