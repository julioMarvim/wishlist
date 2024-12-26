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
}
