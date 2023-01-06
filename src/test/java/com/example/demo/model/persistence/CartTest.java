package com.example.demo.model.persistence;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;

class CartTest {

    @Test
    void testAddItem() {
        // create a new cart
        Cart cart = new Cart();

        // create a new item
        Item item = new Item();
        item.setName("Test Item");
        item.setPrice(new BigDecimal(10));

        // add the item to the cart
        cart.addItem(item);

        // verify that the item was added to the cart
        assertEquals(1, cart.getItems().size());
        assertEquals(item, cart.getItems().get(0));

        // verify that the total price was updated
        assertEquals(new BigDecimal(10), cart.getTotal());
    }

    @Test
    void testRemoveItem() {
        // create a new cart
        Cart cart = new Cart();

        // create a new item
        Item item = new Item();
        item.setName("Test Item");
        item.setPrice(new BigDecimal(10));

        // add the item to the cart
        cart.addItem(item);

        // remove the item from the cart
        cart.removeItem(item);

        // verify that the item was removed from the cart
        assertEquals(0, cart.getItems().size());

        // verify that the total price was updated
        assertEquals(new BigDecimal(0), cart.getTotal());
    }
}

