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
        ProductResponseOutput productEntity1 = new ProductResponseOutput("product-id-1", "Garrafa", "Garrafa de café");
        ProductResponseOutput productEntity2 = new ProductResponseOutput("product-id-2", "Caneca", "Caneca térmica");

        wishlistResponseOutput = new WishlistResponseOutput("wishlist-id", "client-id", List.of(productEntity1, productEntity2));
    }

    @Test
    void shouldReturnWishlist() {
        when(wishlistRepository.findOrCreate("client-id")).thenReturn(wishlistResponseOutput);

        WishlistResponseInput result = useCase.execute("client-id");

        assertNotNull(result);
        assertEquals("client-id", result.clientId());
        assertEquals(2, result.products().size());
        assertEquals("product-id-1", result.products().get(0).id());
        assertEquals("product-id-2", result.products().get(1).id());

        verify(wishlistRepository, times(1)).findOrCreate("client-id");
    }
}
