package com.example.demo.services;

import com.example.demo.dto.PaymentInfo;
import com.example.demo.dto.Purchase;
import com.example.demo.dto.PurchaseResponse;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;

public interface CheckoutService {

    PurchaseResponse placeOrder(Purchase purchase);

    PaymentIntent createPaymentIntent(PaymentInfo paymentInfo) throws StripeException;
}
