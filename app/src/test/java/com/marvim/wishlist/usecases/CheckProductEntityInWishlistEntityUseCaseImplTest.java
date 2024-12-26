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

        ProductEntity productEntity1 = ProductEntity.builder()
                .id("product-id-1")
                .name("Garrafa")
                .description("Garrafa de café")
                .build();

        ProductEntity productEntity2 = ProductEntity.builder()
                .id("product-id-2")
                .name("Caneca")
                .description("Caneca térmica")
                .build();

        wishlistEntity = WishlistEntity.builder()
                .clientId("client-id")
                .products(List.of(productEntity1, productEntity2))
                .build();
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
