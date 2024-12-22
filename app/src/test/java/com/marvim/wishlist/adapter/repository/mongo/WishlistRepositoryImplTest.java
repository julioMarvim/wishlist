package com.marvim.wishlist.adapter.repository.mongo;

import com.marvim.wishlist.domain.entity.Product;
import com.marvim.wishlist.domain.entity.Wishlist;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

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
                .price(30d)
                .build();

        wishlist = Wishlist.builder()
                .clientId(clientId)
                .products(List.of(product))
                .build();

        wishlistRepository = new WishlistRepositoryImpl(springDataRepository);
    }

    @Test
    void shouldAddProductToWishlist() {
        when(springDataRepository.save(wishlist)).thenReturn(wishlist);

        wishlistRepository.add(product);

        ArgumentCaptor<Wishlist> wishlistCaptor = ArgumentCaptor.forClass(Wishlist.class);
        verify(springDataRepository, times(1)).save(wishlistCaptor.capture());

        List<Product> capturedProducts = wishlistCaptor.getValue().getProducts();
        assert capturedProducts.contains(product);
    }

    @Test
    void shouldRemoveProductFromWishlist() {
        when(springDataRepository.findByClientId(clientId)).thenReturn(java.util.Optional.of(wishlist));

        wishlistRepository.remove(clientId, product.getId());

        ArgumentCaptor<Wishlist> wishlistCaptor = ArgumentCaptor.forClass(Wishlist.class);
        verify(springDataRepository, times(1)).save(wishlistCaptor.capture());

        List<Product> capturedProducts = wishlistCaptor.getValue().getProducts();
        assert !capturedProducts.contains(product);
    }
}
