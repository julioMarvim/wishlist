package com.marvim.wishlist.adapter.repository.mongo;

import com.marvim.wishlist.config.handler.exception.ProductAlreadyInWishlistException;
import com.marvim.wishlist.config.handler.exception.WishlistLimitExceededException;
import com.marvim.wishlist.domain.entity.Product;
import com.marvim.wishlist.domain.entity.Wishlist;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class WishlistRepositoryImplTest {

    @Mock
    private SpringDataWishlistRepository springDataRepository;

    private WishlistRepositoryImpl wishlistRepository;

    private Wishlist wishlist;
    private Product product;
    private final String clientId = "client-id";

    @BeforeEach
    void setUp() {
        product = Product.builder()
                .id("1")
                .name("Garrafa")
                .description("Garrafa de café")
                .build();

        wishlist = Wishlist.builder()
                .id("wishlist-id")
                .clientId(clientId)
                .products(List.of(product))
                .build();

        wishlistRepository = new WishlistRepositoryImpl(springDataRepository);
    }

    @Test
    void shouldAddProductToWishlist() {
        wishlist = Wishlist.builder()
                .id("wishlist-id")
                .clientId(clientId)
                .products(new ArrayList<>())
                .build();

        when(springDataRepository.findByClientId(clientId)).thenReturn(Optional.of(wishlist));
        when(springDataRepository.save(wishlist)).thenReturn(wishlist);

        wishlistRepository.save(clientId, product);

        ArgumentCaptor<Wishlist> wishlistCaptor = ArgumentCaptor.forClass(Wishlist.class);
        verify(springDataRepository, times(1)).save(wishlistCaptor.capture());

        List<Product> capturedProducts = wishlistCaptor.getValue().getProducts();
        assertTrue(capturedProducts.contains(product));
    }

    @Test
    void shouldRemoveProductFromWishlist() {
        wishlist = Wishlist.builder()
                .id("wishlist-id")
                .clientId(clientId)
                .products(new ArrayList<>(List.of(product)))
                .build();

        when(springDataRepository.save(wishlist)).thenReturn(wishlist);

        wishlistRepository.remove(wishlist, product.getId());

        ArgumentCaptor<Wishlist> wishlistCaptor = ArgumentCaptor.forClass(Wishlist.class);
        verify(springDataRepository, times(1)).save(wishlistCaptor.capture());

        List<Product> capturedProducts = wishlistCaptor.getValue().getProducts();
        assertFalse(capturedProducts.contains(product));
    }

    @Test
    void shouldFindWishlistByClientId() {
        when(springDataRepository.findByClientId(clientId)).thenReturn(Optional.of(wishlist));

        Optional<Wishlist> foundWishlistOptional = wishlistRepository.findByClientId(clientId);

        assertTrue(foundWishlistOptional.isPresent());
        Wishlist foundWishlist = foundWishlistOptional.get();
        assertEquals(clientId, foundWishlist.getClientId());
        assertEquals(1, foundWishlist.getProducts().size());
        assertEquals("1", foundWishlist.getProducts().get(0).getId());
    }

    @Test
    void shouldReturnEmptyIfWishlistNotFound() {
        when(springDataRepository.findByClientId(clientId)).thenReturn(Optional.empty());

        Optional<Wishlist> foundWishlistOptional = wishlistRepository.findByClientId(clientId);

        assertFalse(foundWishlistOptional.isPresent());
    }

    @Test
    void shouldThrowExceptionWhenWishlistLimitExceeded() {
        List<Product> products = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            products.add(Product.builder()
                    .id(String.valueOf(i))
                    .name("Product " + i)
                    .description("Description " + i)
                    .build());
        }

        wishlist = Wishlist.builder()
                .id("wishlist-id")
                .clientId(clientId)
                .products(products)
                .build();

        when(springDataRepository.findByClientId(clientId)).thenReturn(Optional.of(wishlist));

        Product productToAdd = Product.builder()
                .id("21")
                .name("New Product")
                .description("New Product Description")
                .build();

        assertThrows(WishlistLimitExceededException.class, () -> wishlistRepository.save(clientId, productToAdd));
    }

    @Test
    void shouldThrowExceptionWhenProductAlreadyInWishlist() {
        wishlist = Wishlist.builder()
                .id("wishlist-id")
                .clientId(clientId)
                .products(new ArrayList<>(List.of(product)))
                .build();

        when(springDataRepository.findByClientId(clientId)).thenReturn(Optional.of(wishlist));

        Product productToAdd = Product.builder()
                .id("1")
                .name("Garrafa")
                .description("Garrafa de café")
                .build();

        assertThrows(ProductAlreadyInWishlistException.class, () -> wishlistRepository.save(clientId, productToAdd));
    }


}
