package com.example.demo.model.persistence;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.math.BigDecimal;

import org.junit.Test;

public class ItemTest {

    @Test
    public void testGettersAndSetters() {
        Item item = new Item();
        String name = "item1";
        BigDecimal price = new BigDecimal(10.99);
        String description = "This is a test item";

        item.setName(name);
        item.setPrice(price);
        item.setDescription(description);

        assertEquals(name, item.getName());
        assertEquals(price, item.getPrice());
        assertEquals(description, item.getDescription());
    }

    @Test
    public void testEquals() {
        Item item1 = new Item();
        Item item2 = new Item();
        item1.setId(1L);
        item2.setId(1L);

        assertEquals(item1, item2);

        item2.setId(2L);
        assertNotEquals(item1, item2);
    }

    @Test
    public void testHashCode() {
        Item item1 = new Item();
        Item item2 = new Item();
        item1.setId(1L);
        item2.setId(1L);

        assertEquals(item1.hashCode(), item2.hashCode());

        item2.setId(2L);
        assertNotEquals(item1.hashCode(), item2.hashCode());
    }
}

