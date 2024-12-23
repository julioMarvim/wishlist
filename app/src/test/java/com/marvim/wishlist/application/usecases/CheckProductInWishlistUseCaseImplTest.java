package com.marvim.wishlist.application.usecases;

import com.marvim.wishlist.domain.entity.Product;
import com.marvim.wishlist.domain.entity.Wishlist;
import com.marvim.wishlist.domain.ports.output.WishlistRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

class CheckProductInWishlistUseCaseImplTest {

    @Mock
    private WishlistRepository wishlistRepository;

    @InjectMocks
    private CheckProductInWishlistUseCaseImpl checkProductInWishlistUseCase;

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

        boolean result = checkProductInWishlistUseCase.execute(clientId, productId);

        assertThat(result).isTrue();
    }

    @Test
    void shouldReturnFalseWhenProductDoesNotExistInWishlist() {
        String clientId = "client-id";
        String productId = "non-existing-product-id";

        when(wishlistRepository.findByClientId(clientId)).thenReturn(Optional.of(wishlist));

        boolean result = checkProductInWishlistUseCase.execute(clientId, productId);

        assertThat(result).isFalse();
    }
}
