package com.marvim.wishlist.usecases;

import com.marvim.wishlist.output.WishlistRepository;
import com.marvim.wishlist.usecases.RemoveProductFromWishlistUseCaseImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RemoveProductEntityFromWishlistEntityUseCaseImplTest {

    @Mock
    private WishlistRepository wishlistRepository;

    @InjectMocks
    private RemoveProductFromWishlistUseCaseImpl useCase;

    @Test
    void shouldRemoveProductFromWishlist() {
        String clientId = "client-id";
        String productId = "product-id";

        doNothing().when(wishlistRepository).remove(clientId, productId);

        useCase.execute(clientId, productId);

        verify(wishlistRepository, times(1)).remove(clientId, productId);
    }

    /*@Test
    void shouldThrowWishlistNotFoundExceptionWhenWishlistNotFound() {
        String clientId = "non-existent-client-id";
        String productId = "product-id";

        when(wishlistRepository.findByClientId(clientId)).thenReturn(Optional.empty());

        WishlistNotFoundException exception = assertThrows(WishlistNotFoundException.class,() ->
                useCase.execute(clientId, productId)
        );

        assertEquals("WishlistEntity not found for cilent: non-existent-client-id", exception.getMessage());
        verify(wishlistRepository, times(1)).findByClientId(clientId);
    }*/

    /*@Test
    void shouldThrowProductNotFoundExceptionWhenProductNotFoundInClientWishlist() {
        String clientId = "client-id";
        String productId = "non-existent-product-id";

        ProductEntity existingProduct = ProductEntity.builder()
                .id("product-id")
                .name("Garrafa")
                .description("Garrafa de café")
                .build();

        WishlistEntity wishlist = WishlistEntity.builder()
                .id("wishlist-1")
                .clientId(clientId)
                .productyEntities(new ArrayList<>(List.of(existingProduct)))
                .build();

        when(wishlistRepository.findByClientId(clientId)).thenReturn(wishlist);

        ProductNotFoundException exception = assertThrows(ProductNotFoundException.class,() ->
                useCase.execute(clientId, productId)
        );

        assertEquals("ProductEntity non-existent-product-id not found in customer wishlist with id: client-id", exception.getMessage());
        verify(wishlistRepository, times(1)).findByClientId(clientId);
    }*/
}
