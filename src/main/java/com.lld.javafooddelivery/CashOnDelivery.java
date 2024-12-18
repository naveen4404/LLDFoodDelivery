package com.johndoe.javafooddelivery;
public class CashOnDelivery implements Payment {
    @Override
    public void processPayment(double amount) {
        System.out.println("Cash on delivery selected for $" + amount);
    }

    @Override
    public String getPaymentType() {
        return "Cash on Delivery";
    }
}
