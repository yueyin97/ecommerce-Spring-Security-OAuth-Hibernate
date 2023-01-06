package com.example.demo.controllers;

import com.example.demo.TestUtils;
import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.UserOrder;
import com.example.demo.model.persistence.repositories.OrderRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.junit.Assert.assertEquals;

public class OrderControllerTest {

    private OrderController orderController;
    private UserRepository userRepo = mock(UserRepository.class);
    private OrderRepository orderRepo = mock(OrderRepository.class);

    private User user;
    private Cart cart;
    private Item item1;
    private Item item2;
    private UserOrder order1;
    private UserOrder order2;

    @Before
    public void setup() {
        orderController = new OrderController();
        TestUtils.injectObjects(orderController, "userRepository", userRepo);
        TestUtils.injectObjects(orderController, "orderRepository", orderRepo);

        User user = new User();
        user.setUsername("user");

        item1 = new Item();
        item1.setName("Item 1");
        item1.setPrice(BigDecimal.valueOf(10));

        item2 = new Item();
        item2.setName("Item 2");
        item2.setPrice(BigDecimal.valueOf(15));

        cart = new Cart();
        cart.setUser(user);
        cart.setItems(Arrays.asList(item1, item1, item2, item2, item2));
        cart.setTotal(BigDecimal.valueOf(65));
        user.setCart(cart);

        order1 = new UserOrder();
        order1.setUser(user);
        order1.setItems(Arrays.asList(item1));
        order1.setTotal(BigDecimal.valueOf(10));

        order2 = new UserOrder();
        order2.setUser(user);
        order2.setItems(Arrays.asList(item1, item1, item2, item2, item2));
        order2.setTotal(BigDecimal.valueOf(65));

        when(userRepo.findByUsername("user")).thenReturn(user);
        when(orderRepo.findByUser(user)).thenReturn(Arrays.asList(order1, order2));

    }

    @Test
    public void testSubmit() {
        ResponseEntity<UserOrder> response = orderController.submit("user");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(cart.getItems(), response.getBody().getItems());
        assertEquals(cart.getTotal(), response.getBody().getTotal());
    }

    @Test
    public void testSubmit_EmptyCart() {
        cart.setItems(Arrays.asList());
        cart.setUser(user);

        ResponseEntity<UserOrder> response = orderController.submit("user");

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testGetOrdersForUsers() {
        ResponseEntity<List<UserOrder>> response = orderController.getOrdersForUser("user");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(Arrays.asList(order1, order2), response.getBody());
    }

    @Test
    public void testGetOrdersForUsers_NullUser() {
        ResponseEntity<List<UserOrder>> response = orderController.getOrdersForUser(null);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

}
