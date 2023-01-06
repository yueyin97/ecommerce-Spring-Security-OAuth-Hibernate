package com.example.demo.dto;

import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.UserOrder;


public class Purchase {

    private User user;

    private String shippingAddress;

    private String billingAddress;

    private UserOrder order;

    public Purchase() {
    }

    public Purchase(User user, String shippingAddress, String billingAddress, UserOrder order) {
        this.user = user;
        this.shippingAddress = shippingAddress;
        this.billingAddress = billingAddress;
        this.order = order;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(String shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public String getBillingAddress() {
        return billingAddress;
    }

    public void setBillingAddress(String billingAddress) {
        this.billingAddress = billingAddress;
    }

    public UserOrder getOrder() {
        return order;
    }

    public void setOrder(UserOrder order) {
        this.order = order;
    }
}
