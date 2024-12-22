package com.marvim.wishlist.adapter.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.marvim.wishlist.adapter.controller.dto.request.AddProductRequest;
import com.marvim.wishlist.domain.entity.Product;
import com.marvim.wishlist.domain.ports.input.AddProductToWishlistUseCase;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(WishlistController.class)
public class WishlistControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private AddProductToWishlistUseCase addProductToWishlistUseCase;

    @Test
    void shouldAddProductToWishlist() throws Exception {
        AddProductRequest request = AddProductRequest.builder()
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

        ArgumentCaptor<Product> productCaptor = ArgumentCaptor.forClass(Product.class);
        verify(addProductToWishlistUseCase, times(1)).execute(productCaptor.capture());
    }

}