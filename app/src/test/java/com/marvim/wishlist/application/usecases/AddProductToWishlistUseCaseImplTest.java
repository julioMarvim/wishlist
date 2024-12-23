package com.marvim.wishlist.application.usecases;

import com.marvim.wishlist.config.exception.ProductAlreadyInWishlistException;
import com.marvim.wishlist.domain.entity.Product;
import com.marvim.wishlist.domain.entity.Wishlist;
import com.marvim.wishlist.domain.ports.output.WishlistRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AddProductToWishlistUseCaseImplTest {

    @Mock
    private WishlistRepository wishlistRepository;

    @InjectMocks
    private AddProductToWishlistUseCaseImpl addProductToWishlistUseCase;

    @Test
    void shouldSaveProductWhenExecutingUseCase() {
        String clientId = "client-id";
        Product product = Product.builder()
                .id("product-id")
                .name("Garrafa")
                .description("Garrafa de café")
                .build();

        Wishlist existingWishlist = Wishlist.builder()
                .id("wishlist-1")
                .clientId(clientId)
                .products(new ArrayList<>())
                .build();

        when(wishlistRepository.findByClientId(clientId)).thenReturn(Optional.of(existingWishlist));

        addProductToWishlistUseCase.execute(clientId, product);

        ArgumentCaptor<Wishlist> captor = ArgumentCaptor.forClass(Wishlist.class);
        verify(wishlistRepository).save(captor.capture());

        Wishlist capturedWishlist = captor.getValue();
        assertEquals(clientId, capturedWishlist.getClientId());
        assertTrue(capturedWishlist.getProducts().contains(product));
    }

    @Test
    void shouldThrowExceptionIfWishlistExceedsMaximumProducts() {
        String clientId = "client-id";

        Wishlist fullWishlist = Wishlist.builder()
                .id("wishlist-1")
                .clientId(clientId)
                .products(IntStream.range(0, 20)
                        .mapToObj(i -> Product.builder()
                                .id("product-" + i)
                                .name("Produto " + i)
                                .description("Descrição " + i)
                                .build())
                        .collect(Collectors.toList()))
                .build();

        Product newProduct = Product.builder()
                .id("new-product-id")
                .name("Novo Produto")
                .description("Novo Produto Descrição")
                .build();

        when(wishlistRepository.findByClientId(clientId)).thenReturn(Optional.of(fullWishlist));

        IllegalStateException exception = assertThrows(IllegalStateException.class, () ->
                addProductToWishlistUseCase.execute(clientId, newProduct)
        );

        assertEquals("A wishlist já contém o número máximo de produtos.", exception.getMessage());
        verify(wishlistRepository, never()).save(any());
    }

    @Test
    void shouldCreateNewWishlistIfNoneExists() {
        String clientId = "new-client-id";

        Product product = Product.builder()
                .id("product-id")
                .name("Garrafa")
                .description("Garrafa de café")
                .build();

        when(wishlistRepository.findByClientId(clientId)).thenReturn(Optional.empty());

        addProductToWishlistUseCase.execute(clientId, product);

        ArgumentCaptor<Wishlist> captor = ArgumentCaptor.forClass(Wishlist.class);
        verify(wishlistRepository).save(captor.capture());

        Wishlist capturedWishlist = captor.getValue();
        assertEquals(clientId, capturedWishlist.getClientId());
        assertTrue(capturedWishlist.getProducts().contains(product));
    }

    @Test
    void shouldThrowExceptionIfProductAlreadyInWishlist() {
        String clientId = "client-id";

        Product existingProduct = Product.builder()
                .id("product-id")
                .name("Garrafa")
                .description("Garrafa de café")
                .build();

        Wishlist wishlist = Wishlist.builder()
                .id("wishlist-1")
                .clientId(clientId)
                .products(new ArrayList<>(List.of(existingProduct)))
                .build();

        when(wishlistRepository.findByClientId(clientId)).thenReturn(Optional.of(wishlist));

        ProductAlreadyInWishlistException exception = assertThrows(ProductAlreadyInWishlistException.class, () ->
                addProductToWishlistUseCase.execute(clientId, existingProduct)
        );

        assertEquals("Produto com ID product-id já está na wishlist do cliente: client-id", exception.getMessage());
        verify(wishlistRepository, never()).save(any());
    }

}
