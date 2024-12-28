package com.marvim.wishlist.usecases;

import com.marvim.wishlist.input.dto.response.WishlistResponseInput;
import com.marvim.wishlist.output.WishlistRepository;
import com.marvim.wishlist.output.dto.response.ProductResponseOutput;
import com.marvim.wishlist.output.dto.response.WishlistResponseOutput;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class GetWishlistEntityUseCaseImplTest {

    @Mock
    private WishlistRepository wishlistRepository;

    @InjectMocks
    private GetWishlistUseCaseImpl useCase;

    private WishlistResponseOutput wishlistResponseOutput;

    @BeforeEach
    void setUp() {
        ProductResponseOutput productEntity1 = ProductResponseOutput.builder()
                .id("product-id-1")
                .name("Garrafa")
                .description("Garrafa de café")
                .build();

        ProductResponseOutput productEntity2 = ProductResponseOutput.builder()
                .id("product-id-2")
                .name("Caneca")
                .description("Caneca térmica")
                .build();

        wishlistResponseOutput = WishlistResponseOutput.builder()
                .id("wishlist-id")
                .clientId("client-id")
                .products(List.of(productEntity1, productEntity2))
                .build();
    }

    @Test
    void shouldReturnWishlist() {
        when(wishlistRepository.findOrCreate("client-id")).thenReturn(wishlistResponseOutput);

        WishlistResponseInput result = useCase.execute("client-id");

        assertNotNull(result);
        assertEquals("client-id", result.getClientId());
        assertEquals(2, result.getProducts().size());
        assertEquals("product-id-1", result.getProducts().get(0).getId());
        assertEquals("product-id-2", result.getProducts().get(1).getId());

        verify(wishlistRepository, times(1)).findOrCreate("client-id");
    }
}
