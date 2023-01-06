package com.example.demo.services;

import com.example.demo.dto.PaymentInfo;
import com.example.demo.dto.Purchase;
import com.example.demo.dto.PurchaseResponse;
import com.example.demo.model.persistence.UserOrder;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CheckoutServiceImpl implements CheckoutService {

    public CheckoutServiceImpl(@Value("${stripe.key.secret}") String secretKey) {
        // initialize Stripe API with secret key
        Stripe.apiKey = secretKey;
    }

    @Override
    public PurchaseResponse placeOrder(Purchase purchase) {

        // generate tracking number
        String orderTrackingNumber = generateOrderTrackingNumber();

        // return a response
        return new PurchaseResponse(orderTrackingNumber);
    }

    @Override
    public PaymentIntent createPaymentIntent(PaymentInfo paymentInfo) throws StripeException {

        List<String> paymentMethods = new ArrayList<>();
        paymentMethods.add("card");

        Map<String, Object> params = new HashMap<>();
        params.put("amount", paymentInfo.getAmount());
        params.put("currency", paymentInfo.getCurrency());
        params.put("payment_method_types", paymentMethods);
        params.put("description", "yueyin online store purchase");

        return PaymentIntent.create(params);

    }

    private String generateOrderTrackingNumber() {

        return UUID.randomUUID().toString();
    }
}
