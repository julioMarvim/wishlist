package com.marvim.wishlist.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.marvim.wishlist.controller.dto.request.AddProductRequest;
import com.marvim.wishlist.controller.dto.response.ApiResponseDto;
import com.marvim.wishlist.controller.dto.response.WishlistResponseDto;
import com.marvim.wishlist.input.dto.response.ProductResponseInputDto;
import com.marvim.wishlist.input.dto.response.WishlistResponseInputDto;
import com.marvim.wishlist.input.AddProductToWishlistUseCase;
import com.marvim.wishlist.input.CheckProductInWishlistUseCase;
import com.marvim.wishlist.input.GetWishlistUseCase;
import com.marvim.wishlist.input.RemoveProductFromWishlistUseCase;
import com.marvim.wishlist.input.dto.request.AddProductRequestInputDto;
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
public class WishlistEntityControllerTest {

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
    private WishlistResponseInputDto wishlistResponse;

    @BeforeEach
    void setUp() {
        ProductResponseInputDto productEntity1 = ProductResponseInputDto.builder()
                .id("product-id-1")
                .name("Garrafa")
                .description("Garrafa de café")
                .build();

        ProductResponseInputDto productEntity2 = ProductResponseInputDto.builder()
                .id("product-id-2")
                .name("Caneca")
                .description("Caneca térmica")
                .build();

        wishlistResponse = WishlistResponseInputDto.builder()
                .clientId("client-id")
                .products(List.of(productEntity1, productEntity2))
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

        mockMvc.perform(post("/api/v1/wishlist/{clientId}", clientId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(request)))
                .andExpect(status().isCreated());

        ArgumentCaptor<AddProductRequestInputDto> productCaptor = ArgumentCaptor.forClass(AddProductRequestInputDto.class);
        verify(addProductToWishlistUseCase, times(1)).execute(eq(clientId), productCaptor.capture());
    }

    @Test
    void shouldRemoveProductFromWishlist() throws Exception {
        String clientId = "client-id";
        String productId = "product-id";

        mockMvc.perform(delete("/api/v1/wishlist/{clientId}/{productId}", clientId, productId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(removeProductFromWishlistUseCase).execute(clientId, productId);
    }

    @Test
    void shouldGetWishlist() throws Exception {
        String clientId = "client-id";

        when(getWishlistUseCase.execute(clientId)).thenReturn(wishlistResponse);

        mockMvc.perform(get("/api/v1/wishlist/{clientId}", clientId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(result -> {
                    String content = result.getResponse().getContentAsString();

                    ApiResponseDto<WishlistResponseDto> response = new ObjectMapper()
                            .readValue(content, new TypeReference<>() {});

                    assertThat(response.data().getProducts()).hasSize(2);
                    assertThat(response.data().getProducts().get(0).getId()).isEqualTo("product-id-1");
                    assertThat(response.data().getProducts().get(1).getId()).isEqualTo("product-id-2");
                });

        verify(getWishlistUseCase).execute(clientId);
    }

    @Test
    void shouldCheckProductInWishlist() throws Exception {
        String clientId = "client-id";
        String productId = "product-id-1";

        doNothing().when(checkProductInWishlistUseCase).execute(clientId, productId);

        mockMvc.perform(get("/api/v1/wishlist/{clientId}/{productId}/exists", clientId, productId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(result -> {
                    String content = result.getResponse().getContentAsString();
                    assertThat(content).isEmpty();
                });

        verify(checkProductInWishlistUseCase).execute(clientId, productId);
    }
}
