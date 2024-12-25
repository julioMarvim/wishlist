package com.marvim.wishlist.application.usecases;

import com.marvim.wishlist.config.handler.exception.WishlistNotFoundException;
import com.marvim.wishlist.domain.entity.Product;
import com.marvim.wishlist.domain.entity.Wishlist;
import com.marvim.wishlist.domain.ports.output.WishlistRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class GetWishlistUseCaseImplTest {

    @Mock
    private WishlistRepository wishlistRepository;

    @InjectMocks
    private GetWishlistUseCaseImpl useCase;

    private Wishlist wishlist;

    @BeforeEach
    void setUp() {
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
    void shouldReturnWishlist() {
        when(wishlistRepository.findByClientId("client-id")).thenReturn(wishlist);

        Wishlist result = useCase.execute("client-id");

        assertNotNull(result);
        assertEquals("client-id", result.getClientId());
        assertEquals(2, result.getProducts().size());
        assertEquals("product-id-1", result.getProducts().get(0).getId());
        assertEquals("product-id-2", result.getProducts().get(1).getId());

        verify(wishlistRepository, times(1)).findByClientId("client-id");
    }

    /*@Test
    void shouldThrowWishlistNotFoundExceptionWhenWishlistNotFound() {
        String clientId = "non-existent-client-id";

        when(wishlistRepository.findByClientId(clientId)).thenReturn(Optional.empty());

        WishlistNotFoundException exception = assertThrows(WishlistNotFoundException.class,() ->
                useCase.execute(clientId)
        );

        assertEquals("Wishlist not found for cilent: non-existent-client-id", exception.getMessage());
        verify(wishlistRepository, times(1)).findByClientId(clientId);
    }*/

}
