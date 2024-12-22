package com.marvim.wishlist.application.usecases;

import com.marvim.wishlist.domain.ports.output.WishlistRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

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

        useCase.execute(clientId, productId);

        verify(wishlistRepository, times(1)).remove(clientId, productId);
    }
}
