package com.marvim.wishlist.domain.service;

import com.marvim.wishlist.config.handler.exception.WishlistLimitExceededException;
import com.marvim.wishlist.domain.entity.Product;
import com.marvim.wishlist.domain.entity.Wishlist;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class WishlistValidationServiceTest {

    @Mock
    private Wishlist wishlist;

    @InjectMocks
    private WishlistValidationService wishlistValidationService;

    @Test
    void shouldThrowExceptionIfWishlistExceedsMaximumLimit() {
        String clientId = "client-id";
        Wishlist fullWishlist = Wishlist.builder()
                .clientId(clientId)
                .products(IntStream.range(0, 20)
                        .mapToObj(i -> Product.builder()
                                .id("product-" + i)
                                .name("Produto " + i)
                                .description("Descrição " + i)
                                .build())
                        .collect(Collectors.toList()))
                .build();

        WishlistLimitExceededException exception = assertThrows(WishlistLimitExceededException.class, () ->
                wishlistValidationService.validateWishlistLimit(fullWishlist)
        );

        assertEquals("Customer ID client-id has exceeded the maximum number of products in the wishlist.", exception.getMessage());
    }

    @Test
    void shouldNotThrowExceptionIfWishlistIsUnderLimit() {
        Wishlist wishlist = Wishlist.builder()
                .clientId("client-id")
                .products(new ArrayList<>(List.of(Product.builder().id("product-1").build())))
                .build();

        assertDoesNotThrow(() -> wishlistValidationService.validateWishlistLimit(wishlist));
    }
}
