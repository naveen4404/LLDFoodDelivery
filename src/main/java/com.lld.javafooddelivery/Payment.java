package com.johndoe.javafooddelivery;
public interface Payment {
    void processPayment(double amount);
    String getPaymentType();
}
