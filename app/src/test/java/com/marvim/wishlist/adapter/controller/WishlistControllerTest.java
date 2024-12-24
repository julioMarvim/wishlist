package com.marvim.wishlist.adapter.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.marvim.wishlist.adapter.controller.dto.request.AddProductRequest;
import com.marvim.wishlist.adapter.controller.dto.response.ApiResponse;
import com.marvim.wishlist.adapter.controller.dto.response.CheckProductInWishlistResponse;
import com.marvim.wishlist.adapter.controller.dto.response.WishlistResponse;
import com.marvim.wishlist.domain.entity.Product;
import com.marvim.wishlist.domain.entity.Wishlist;
import com.marvim.wishlist.domain.ports.input.AddProductToWishlistUseCase;
import com.marvim.wishlist.domain.ports.input.CheckProductInWishlistUseCase;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class WishlistControllerTest {

    @Mock
    private AddProductToWishlistUseCase addProductToWishlistUseCase;

    @Mock
    private RemoveProductFromWishlistUseCase removeProductFromWishlistUseCase;

    @Mock
    private GetWishlistUseCase getWishlistUseCase;

    @Mock
    private CheckProductInWishlistUseCase checkProductInWishlistUseCase;

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
                .build();

        Product product2 = Product.builder()
                .id("product-id-2")
                .name("Caneca")
                .description("Caneca térmica")
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

                    ApiResponse<WishlistResponse> response = new ObjectMapper()
                            .readValue(content, new TypeReference<>() {});

                    assertThat(response.getData().getProducts()).hasSize(2);
                    assertThat(response.getData().getProducts().get(0).getId()).isEqualTo("product-id-1");
                    assertThat(response.getData().getProducts().get(1).getId()).isEqualTo("product-id-2");
                });

        verify(getWishlistUseCase).execute(clientId);
    }


    @Test
    void shouldCheckProductInWishlist() throws Exception {
        String clientId = "client-id";
        String productId = "product-id-1";

        when(checkProductInWishlistUseCase.execute(clientId, productId)).thenReturn(true);

        mockMvc.perform(get("/wishlist/{clientId}/{productId}/exists", clientId, productId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(result -> {
                    String content = result.getResponse().getContentAsString();
                    ApiResponse<CheckProductInWishlistResponse> response = new ObjectMapper()
                            .readValue(content, new TypeReference<>() {});

                    assertThat(response.getData().getExists()).isTrue();
                });

        verify(checkProductInWishlistUseCase).execute(clientId, productId);
    }

}
