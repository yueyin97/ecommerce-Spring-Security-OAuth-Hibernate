package com.example.demo.controllers;

import com.example.demo.TestUtils;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.repositories.CartRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import com.example.demo.model.requests.CreateUserRequest;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

public class UserControllerTest {

    private UserController userController;
    private UserRepository userRepo = mock(UserRepository.class);
    private CartRepository cartRepo = mock(CartRepository.class);
    private BCryptPasswordEncoder encoder = mock(BCryptPasswordEncoder.class);

    private CreateUserRequest request;
    private ResponseEntity<User> response;
    private User user;
    private User yy;
    private Long id;

    @Before
    public void setup() {
        // set up for creating user tests
        userController = new UserController();
        TestUtils.injectObjects(userController, "userRepository", userRepo);
        TestUtils.injectObjects(userController, "cartRepository", cartRepo);
        TestUtils.injectObjects(userController, "bCryptPasswordEncoder", encoder);

        request = new CreateUserRequest();
        request.setUsername("test");
        request.setPassword("test123");
        request.setConfirmPassword("test123");

        response = userController.createUser(request);
        user = response.getBody();

        when(encoder.encode("test123")).thenReturn("hashedpassword");

        // set up for finding user tests
        yy = new User();
        id = yy.getId();
        yy.setUsername("yy");
    }

    @Test
    public void testCreateUser() {
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        verify(userRepo).save(user);
        verify(cartRepo).save(user.getCart());
    }

    @Test
    public void testCreateUser_InvalidPassword() {
        request.setPassword("short");
        request.setConfirmPassword("short");

        response = userController.createUser(request);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void testCreateUser_MismatchPassword() {
        request.setPassword("password");
        request.setConfirmPassword("mismatch");

        response = userController.createUser(request);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void testFindById() {

        when(userRepo.findById(id)).thenReturn(Optional.of(yy));
        response = userController.findById(id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(yy, response.getBody());
        verify(userRepo).findById(0L);
    }

    @Test
    public void testFindById_NotFound() {

        when(userRepo.findById(id)).thenReturn(Optional.empty());
        response = userController.findById(id);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(userRepo).findById(0L);
    }

    @Test
    public void testFindByUsername() {

        when(userRepo.findByUsername("yy")).thenReturn(yy);
        response = userController.findByUserName("yy");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(yy, response.getBody());
        verify(userRepo).findByUsername("yy");
    }

    @Test
    public void testFindByUsername_NotFound() {

        when(userRepo.findByUsername("yy")).thenReturn(null);
        response = userController.findByUserName("yy");

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(userRepo).findByUsername("yy");
    }




}
