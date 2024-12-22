package com.marvim.wishlist.adapter.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.marvim.wishlist.adapter.controller.dto.request.AddProductRequest;
import com.marvim.wishlist.domain.entity.Product;
import com.marvim.wishlist.domain.ports.input.AddProductToWishlistUseCase;
import com.marvim.wishlist.domain.ports.input.RemoveProductFromWishlistUseCase;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
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

    @MockitoBean
    private RemoveProductFromWishlistUseCase removeProductFromWishlistUseCase;

    @Test
    void shouldAddProductToWishlist() throws Exception {
        String clientId = "client-id";
        AddProductRequest request = AddProductRequest.builder()
                .id("product-id")
                .name("Garrafa")
                .description("Garrafa de café")
                .price(30d)
                .build();

        mockMvc.perform(post("/wishlist/{clientId}", clientId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated());

        ArgumentCaptor<Product> productCaptor = ArgumentCaptor.forClass(Product.class);
        verify(addProductToWishlistUseCase, times(1)).execute(eq(clientId), productCaptor.capture());
    }

    @Test
    void shouldRemoveProductFromWishlist() throws Exception {
        String clientId = "client-id";
        String productId = "product-id";

        mockMvc.perform(delete("/wishlist/{clientId}/{productId}", clientId, productId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(removeProductFromWishlistUseCase).execute(clientId, productId);
    }

}