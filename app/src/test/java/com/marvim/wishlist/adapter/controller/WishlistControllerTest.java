package com.marvim.wishlist.adapter.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.marvim.wishlist.adapter.controller.dto.request.AddProductRequest;
import com.marvim.wishlist.adapter.controller.dto.response.WishlistResponse;
import com.marvim.wishlist.domain.entity.Product;
import com.marvim.wishlist.domain.entity.Wishlist;
import com.marvim.wishlist.domain.ports.input.AddProductToWishlistUseCase;
import com.marvim.wishlist.domain.ports.input.GetWishlistUseCase;
import com.marvim.wishlist.domain.ports.input.RemoveProductFromWishlistUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class WishlistControllerTest {

    @Mock
    private AddProductToWishlistUseCase addProductToWishlistUseCase;

    @Mock
    private RemoveProductFromWishlistUseCase removeProductFromWishlistUseCase;

    @Mock
    private GetWishlistUseCase getWishlistUseCase;

    @InjectMocks
    private WishlistController wishlistController;

    private MockMvc mockMvc;
    private Wishlist wishlist;

    @BeforeEach
    void setUp() {
        Product product1 = Product.builder()
                .id("product-id-1")
                .name("Garrafa")
                .description("Garrafa de café")
                .price(30d)
                .build();

        Product product2 = Product.builder()
                .id("product-id-2")
                .name("Caneca")
                .description("Caneca térmica")
                .price(20d)
                .build();

        wishlist = Wishlist.builder()
                .clientId("client-id")
                .products(List.of(product1, product2))
                .build();

        mockMvc = MockMvcBuilders.standaloneSetup(wishlistController).build();
    }

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
                        .content(new ObjectMapper().writeValueAsString(request)))
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

    @Test
    void shouldGetWishlist() throws Exception {
        String clientId = "client-id";

        when(getWishlistUseCase.execute(clientId)).thenReturn(wishlist);

        mockMvc.perform(get("/wishlist/{clientId}", clientId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(result -> {
                    String content = result.getResponse().getContentAsString();
                    WishlistResponse responseWishlist = new ObjectMapper().readValue(content, WishlistResponse.class);
                    assertThat(responseWishlist.getProducts()).hasSize(2);
                    assertThat(responseWishlist.getProducts().get(0).getId()).isEqualTo("product-id-1");
                    assertThat(responseWishlist.getProducts().get(1).getId()).isEqualTo("product-id-2");
                });

        verify(getWishlistUseCase).execute(clientId);
    }
}
