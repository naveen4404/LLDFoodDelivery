package com.johndoe.javafooddelivery;
public class Card implements Payment {
    @Override
    public void processPayment(double amount) {
        System.out.println("Processing Card payment of $" + amount);
    }

    @Override
    public String getPaymentType() {
        return "Card";
    }
}