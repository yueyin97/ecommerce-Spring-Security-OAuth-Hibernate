package com.example.demo.dto;

public class PaymentInfo {

    private int amount;

    private String currency;

    public PaymentInfo() {
    }

    public PaymentInfo(int amount, String currency) {
        this.amount = amount;
        this.currency = currency;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}
