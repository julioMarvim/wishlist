package com.marvim.wishlist.application.usecases;

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

    /*@Test
    void shouldReturnProductNotFoundExceptionWhenProductDoesNotExistInWishlist() {
        String clientId = "client-id";
        String productId = "non-existing-product-id";

        ProductEntity existingProduct = ProductEntity.builder()
                .id("product-id")
                .name("Garrafa")
                .description("Garrafa de café")
                .build();

        WishlistEntity wishlistEntity = WishlistEntity.builder()
                .id("wishlistEntity-1")
                .clientId(clientId)
                .productyEntities(new ArrayList<>(List.of(existingProduct)))
                .build();

        when(wishlistRepository.findByClientId(clientId)).thenReturn(wishlistEntity);

        ProductNotFoundException exception = assertThrows(ProductNotFoundException.class,() ->
                useCase.execute(clientId, productId)
        );

        assertEquals("ProductEntity non-existing-product-id not found in customer wishlistEntity with id: client-id", exception.getMessage());
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

        assertEquals("WishlistEntity not found for cilent: non-existent-client-id", exception.getMessage());
        verify(wishlistRepository, times(1)).findByClientId(clientId);
    }*/
}
