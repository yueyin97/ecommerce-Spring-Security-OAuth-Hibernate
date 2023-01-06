package com.example.demo.controllers;

import java.util.Optional;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Optional;

import com.example.demo.TestUtils;
import com.example.demo.model.persistence.repositories.CartRepository;
import com.example.demo.model.persistence.repositories.ItemRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.User;
import com.example.demo.model.requests.ModifyCartRequest;


public class CartControllerTest {

    private CartController cartController;
    private UserRepository userRepo = mock(UserRepository.class);
    private CartRepository cartRepo = mock(CartRepository.class);
    private ItemRepository itemRepo = mock(ItemRepository.class);

    private User user;
    private Item item;
    private Cart cart;
    private ModifyCartRequest request;

    @Before
    public void setup() {
        cartController = new CartController();
        TestUtils.injectObjects(cartController, "userRepository", userRepo);
        TestUtils.injectObjects(cartController, "cartRepository", cartRepo);
        TestUtils.injectObjects(cartController, "itemRepository", itemRepo);

        user = new User();
        user.setUsername("user");
        user.setCart(new Cart());

        item = new Item();
        item.setName("test item");
        item.setPrice(BigDecimal.valueOf(10));

        cart = new Cart();
        cart.setItems(Arrays.asList(item, item));
        cart.setTotal(BigDecimal.valueOf(20));
        cart.setUser(user);

        request = new ModifyCartRequest();
        request.setUsername("user");
        request.setItemId(0L);
        request.setQuantity(2);

        when(userRepo.findByUsername("user")).thenReturn(user);
        when(itemRepo.findById(0L)).thenReturn(Optional.of(item));
        when(cartRepo.save(cart)).thenReturn(cart);
    }

    @Test
    public void testAddToCart() {
        ResponseEntity<Cart> response = cartController.addTocart(request);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(cart.getItems(), response.getBody().getItems());
        assertEquals(cart.getTotal(), response.getBody().getTotal());
    }

    @Test
    public void testAddToCart_NullUser() {
        when(userRepo.findByUsername("user")).thenReturn(null);

        ResponseEntity<Cart> response = cartController.addTocart(request);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testAddToCart_NullItem() {
        when(itemRepo.findById(0L)).thenReturn(null);

        ResponseEntity<Cart> response = cartController.addTocart(request);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testRemoveFromCart() {

        Cart modifiedCart = new Cart();
        modifiedCart.setItems(Arrays.asList());
        modifiedCart.setTotal(BigDecimal.valueOf(0));

        when(cartRepo.save(cart)).thenReturn(modifiedCart);

        ResponseEntity<Cart> response = cartController.removeFromcart(request);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(cart.getItems(), response.getBody().getItems());
        assertEquals(cart.getTotal(), response.getBody().getTotal());
    }

    @Test
    public void testRemoveFromCart_EmptyCart() {
        request.setQuantity(3);

        ResponseEntity<Cart> response = cartController.removeFromcart(request);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void testRemoveFromCart_NullUser() {
        when(userRepo.findByUsername("user")).thenReturn(null);

        ResponseEntity<Cart> response = cartController.removeFromcart(request);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testRemoveFromCart_NullItem() {
        when(itemRepo.findById(0L)).thenReturn(null);

        ResponseEntity<Cart> response = cartController.removeFromcart(request);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }



}


