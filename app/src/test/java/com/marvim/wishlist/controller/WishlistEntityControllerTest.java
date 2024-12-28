package com.marvim.wishlist.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.marvim.wishlist.controller.dto.request.AddProductRequest;
import com.marvim.wishlist.controller.dto.response.ApiResponse;
import com.marvim.wishlist.controller.dto.response.WishlistRespons;
import com.marvim.wishlist.input.dto.response.ProductResponseInput;
import com.marvim.wishlist.input.dto.response.WishlistResponseInput;
import com.marvim.wishlist.input.AddProductToWishlistUseCase;
import com.marvim.wishlist.input.CheckProductInWishlistUseCase;
import com.marvim.wishlist.input.GetWishlistUseCase;
import com.marvim.wishlist.input.RemoveProductFromWishlistUseCase;
import com.marvim.wishlist.input.dto.request.AddProductRequestInput;
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
    private WishlistControllerImpl wishlistControllerImpl;

    private MockMvc mockMvc;
    private WishlistResponseInput wishlistResponse;

    @BeforeEach
    void setUp() {
        ProductResponseInput productEntity1 = new ProductResponseInput("product-id-1", "name", "desciption");
        ProductResponseInput productEntity2 = new ProductResponseInput("product-id-2", "name", "desciption");

        wishlistResponse = new WishlistResponseInput("id", "client-id", List.of(productEntity1, productEntity2));

        mockMvc = MockMvcBuilders.standaloneSetup(wishlistControllerImpl).build();
    }

    @Test
    void shouldAddProductToWishlist() throws Exception {
        String clientId = "client-id";
        AddProductRequest request = new AddProductRequest("product-id", "name", "description");

        mockMvc.perform(post("/api/v1/wishlist/{clientId}", clientId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(request)))
                .andExpect(status().isCreated());

        ArgumentCaptor<AddProductRequestInput> productCaptor = ArgumentCaptor.forClass(AddProductRequestInput.class);
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

                    ApiResponse<WishlistRespons> response = new ObjectMapper()
                            .readValue(content, new TypeReference<>() {
                            });

                    assertThat(response.data().products()).hasSize(2);
                    assertThat(response.data().products().get(0).id()).isEqualTo("product-id-1");
                    assertThat(response.data().products().get(1).id()).isEqualTo("product-id-2");
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
