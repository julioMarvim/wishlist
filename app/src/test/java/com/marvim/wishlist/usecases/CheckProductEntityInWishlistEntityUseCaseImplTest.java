package com.marvim.wishlist.usecases;

import com.marvim.wishlist.repository.entity.ProductEntity;
import com.marvim.wishlist.repository.entity.WishlistEntity;
import com.marvim.wishlist.output.WishlistRepository;
import com.marvim.wishlist.usecases.CheckProductInWishlistUseCaseImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class CheckProductEntityInWishlistEntityUseCaseImplTest {

    @Mock
    private WishlistRepository wishlistRepository;

    @InjectMocks
    private CheckProductInWishlistUseCaseImpl useCase;

    private WishlistEntity wishlistEntity;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        ProductEntity productEntity1 = new ProductEntity("product-id-1", "Garrafa","Garrafa de café");
        ProductEntity productEntity2 =  new ProductEntity("product-id-2", "Caneca","Caneca térmica");

        wishlistEntity = new WishlistEntity(null, "client-id", List.of(productEntity1, productEntity2));
    }

    @Test
    void shouldFindTheProductWishlist() {
        String clientId = "client-id";
        String productId = "product-id-1";

        doNothing().when(wishlistRepository).checkProductInWishlist(clientId, productId);

        useCase.execute(clientId, productId);

        verify(wishlistRepository, times(1)).checkProductInWishlist(clientId, productId);
    }
}
