package com.marvim.wishlist.adapter.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.marvim.wishlist.adapter.controller.dto.request.InsertProductRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(WishlistController.class)
public class WishlistControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldAddProductToWishlist() throws Exception {
        InsertProductRequest request = InsertProductRequest.builder()
                .id("1")
                .clientId("1")
                .name("Garrafa")
                .description("Garrafa de café")
                .price(30d)
                .build();

        mockMvc.perform(post("/wishlist")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated());
    }

}