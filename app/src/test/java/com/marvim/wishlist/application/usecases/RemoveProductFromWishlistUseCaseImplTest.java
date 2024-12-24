package com.marvim.wishlist.application.usecases;

import com.marvim.wishlist.config.handler.exception.ProductNotFoundException;
import com.marvim.wishlist.config.handler.exception.WishlistNotFoundException;
import com.marvim.wishlist.domain.entity.Product;
import com.marvim.wishlist.domain.entity.Wishlist;
import com.marvim.wishlist.domain.ports.output.WishlistRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RemoveProductFromWishlistUseCaseImplTest {

    @Mock
    private WishlistRepository wishlistRepository;

    @InjectMocks
    private RemoveProductFromWishlistUseCaseImpl useCase;

    @Test
    void shouldRemoveProductFromWishlist() {
        String clientId = "client-id";
        String productId = "product-id";

        Product existingProduct = Product.builder()
                .id(productId)
                .name("Garrafa")
                .description("Garrafa de café")
                .build();

        Wishlist wishlist = Wishlist.builder()
                .id("wishlist-1")
                .clientId(clientId)
                .products(new ArrayList<>(List.of(existingProduct)))
                .build();

        when(wishlistRepository.findByClientId(clientId)).thenReturn(Optional.of(wishlist));

        useCase.execute(clientId, productId);

        verify(wishlistRepository, times(1)).remove(wishlist, productId);
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

    @Test
    void shouldThrowProductNotFoundExceptionWhenProductNotFoundInClientWishlist() {
        String clientId = "client-id";
        String productId = "non-existent-product-id";

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

        assertEquals("Product non-existent-product-id not found in customer wishlist with id: client-id", exception.getMessage());
        verify(wishlistRepository, times(1)).findByClientId(clientId);
    }
}
