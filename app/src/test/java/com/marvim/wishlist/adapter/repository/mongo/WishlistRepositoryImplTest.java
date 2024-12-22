package com.marvim.wishlist.adapter.repository.mongo;

import com.marvim.wishlist.domain.entity.Product;
import com.marvim.wishlist.domain.entity.Wishlist;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class WishlistRepositoryImplTest {
    @Mock
    private SpringDataWishlistRepository springDataRepository;

    @Test
    void shouldAddProductToWishlist() {
        String clientId = "client-id";
        Product product = Product.builder()
                .id("1")
                .name("Garrafa")
                .description("Garrafa de café")
                .price(30d)
                .build();

        Wishlist wishlist = Wishlist.builder()
                .clientId(clientId)
                .products(List.of(product))
                .build();

        springDataRepository.save(wishlist);

        ArgumentCaptor<Wishlist> wishlistCaptor = ArgumentCaptor.forClass(Wishlist.class);
        verify(springDataRepository, times(1)).save(wishlistCaptor.capture());
    }

}