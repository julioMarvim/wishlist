package com.marvim.wishlist.application.usecases;

import com.marvim.wishlist.config.handler.exception.ProductNotFoundException;
import com.marvim.wishlist.config.handler.exception.WishlistNotFoundException;
import com.marvim.wishlist.domain.entity.Product;
import com.marvim.wishlist.domain.entity.Wishlist;
import com.marvim.wishlist.domain.ports.output.WishlistRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class CheckProductInWishlistUseCaseImplTest {

    @Mock
    private WishlistRepository wishlistRepository;

    @InjectMocks
    private CheckProductInWishlistUseCaseImpl useCase;

    private Wishlist wishlist;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

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
    }

    @Test
    void shouldReturnTrueWhenProductExistsInWishlist() {
        String clientId = "client-id";
        String productId = "product-id-1";

        when(wishlistRepository.findByClientId(clientId)).thenReturn(Optional.of(wishlist));

        useCase.execute(clientId, productId);
        verify(wishlistRepository).findByClientId(clientId);
    }

    @Test
    void shouldReturnFalseWhenProductDoesNotExistInWishlist() {
        String clientId = "client-id";
        String productId = "non-existing-product-id";

        Product existingProduct = Product.builder()
                .id("product-id")
                .name("Garrafa")
                .description("Garrafa de café")
                .build();

        Wishlist wishlist = Wishlist.builder()
                .id("wishlist-1")
                .clientId(clientId)
                .products(new ArrayList<>(List.of(existingProduct)))
                .build();

        when(wishlistRepository.findByClientId(clientId)).thenReturn(Optional.of(wishlist));

        ProductNotFoundException exception = assertThrows(ProductNotFoundException.class,() ->
                useCase.execute(clientId, productId)
        );

        assertEquals("Product non-existing-product-id not found in customer wishlist with id: client-id", exception.getMessage());
        verify(wishlistRepository, times(1)).findByClientId(clientId);
    }

    @Test
    void shouldThrowWishlistNotFoundExceptionWhenWishlistNotFound() {
        String clientId = "non-existent-client-id";
        String productId = "product-id";

        when(wishlistRepository.findByClientId(clientId)).thenReturn(Optional.empty());

        WishlistNotFoundException exception = assertThrows(WishlistNotFoundException.class,() ->
                useCase.execute(clientId, productId)
        );

        assertEquals("Wishlist not found for cilent: non-existent-client-id", exception.getMessage());
        verify(wishlistRepository, times(1)).findByClientId(clientId);
    }
}
