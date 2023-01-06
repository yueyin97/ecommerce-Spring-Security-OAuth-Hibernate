package com.example.demo.controllers;

import com.example.demo.TestUtils;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.repositories.ItemRepository;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ItemControllerTest {

    private ItemController itemController;
    private ItemRepository itemRepo = mock(ItemRepository.class);
    private Item item1;
    private Item item2;

    @Before
    public void setup() {
        itemController = new ItemController();
        TestUtils.injectObjects(itemController, "itemRepository", itemRepo);

        item1 = new Item();
        item1.setName("Item 1");
        item1.setPrice(BigDecimal.valueOf(10));

        item2 = new Item();
        item2.setName("Item 2");
        item2.setPrice(BigDecimal.valueOf(20));
    }

    @Test
    public void testGetItems() {

        when(itemRepo.findAll()).thenReturn(Arrays.asList(item1, item2));

        ResponseEntity<List<Item>> response = itemController.getItems();

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).containsExactly(item1, item2);
    }

    @Test
    public void testGetItemById() {

        when(itemRepo.findById(0L)).thenReturn(Optional.of(item1));

        ResponseEntity<Item> response = itemController.getItemById(0L);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(item1);
    }

    @Test
    public void testGetItemById_NotFound() {

        when(itemRepo.findById(3L)).thenReturn(Optional.empty());

        ResponseEntity<Item> response = itemController.getItemById(3L);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody()).isNull();
    }

    @Test
    public void testGetItemsByName() {

        when(itemRepo.findByName("item")).thenReturn(Arrays.asList(item1, item2));

        ResponseEntity<List<Item>> response = itemController.getItemsByName("item");

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).containsExactly(item1, item2);
    }

    @Test
    public void testGetItemsByName_NotFound() {

        when(itemRepo.findByName("item")).thenReturn(Collections.emptyList());

        ResponseEntity<List<Item>> response = itemController.getItemsByName("item");

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody()).isNull();
    }

}
