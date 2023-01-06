package com.example.demo.model.persistence;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

class UserOrderTest {

    @Test
    void testCreateFromCart() {
        // create a new cart
        Cart cart = new Cart();

        // create some items
        Item item1 = new Item();
        item1.setName("Test Item 1");
        item1.setPrice(new BigDecimal(10));

        Item item2 = new Item();
        item2.setName("Test Item 2");
        item2.setPrice(new BigDecimal(20));

        // add the items to the cart
        cart.addItem(item1);
        cart.addItem(item2);

        // set the user on the cart
        User user = new User();
        user.setUsername("test-user");
        cart.setUser(user);

        // create an order from the cart
        UserOrder order = UserOrder.createFromCart(cart);

        // verify that the order has the correct items
        assertEquals(Arrays.asList(item1, item2), order.getItems());

        // verify that the order has the correct total
        assertEquals(new BigDecimal(30), order.getTotal());

        // verify that the order has the correct user
        assertEquals(user, order.getUser());
    }
}

