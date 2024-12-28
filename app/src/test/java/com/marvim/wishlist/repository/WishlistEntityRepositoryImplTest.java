package com.marvim.wishlist.repository;

import com.marvim.wishlist.output.dto.response.WishlistResponseOutput;
import com.marvim.wishlist.repository.entity.ProductEntity;
import com.marvim.wishlist.repository.entity.WishlistEntity;
import com.marvim.wishlist.repository.mapper.AddProductToEntityMapper;
import com.marvim.wishlist.input.exception.ProductAlreadyInWishlistException;
import com.marvim.wishlist.input.exception.ProductNotFoundException;
import com.marvim.wishlist.input.exception.WishlistLimitExceededException;
import com.marvim.wishlist.output.dto.request.AddProductRequestOutput;
import com.marvim.wishlist.repository.mongo.SpringDataWishlistRepository;
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
class WishlistEntityRepositoryImplTest {

    @Mock
    private SpringDataWishlistRepository springDataRepository;

    private WishlistRepositoryImpl wishlistRepository;

    private WishlistEntity wishlistEntity;
    private AddProductRequestOutput addProductRequestOutput;
    private final String clientId = "client-id";

    @BeforeEach
    void setUp() {
        addProductRequestOutput = AddProductRequestOutput.builder()
                .id("1")
                .name("Garrafa")
                .description("Garrafa de café")
                .build();

        wishlistEntity = WishlistEntity.builder()
                .id("wishlistEntity-id")
                .clientId(clientId)
                .products(List.of(AddProductToEntityMapper.toEntity(addProductRequestOutput)))
                .build();

        wishlistRepository = new WishlistRepositoryImpl(springDataRepository);
    }

    @Test
    void shouldAddProductToWishlist() {
        wishlistEntity = WishlistEntity.builder()
                .id("wishlistEntity-id")
                .clientId(clientId)
                .products(new ArrayList<>())
                .build();

        when(springDataRepository.findByClientId(clientId)).thenReturn(Optional.of(wishlistEntity));
        when(springDataRepository.save(wishlistEntity)).thenReturn(wishlistEntity);

        wishlistRepository.save(clientId, addProductRequestOutput);

        ArgumentCaptor<WishlistEntity> wishlistCaptor = ArgumentCaptor.forClass(WishlistEntity.class);
        verify(springDataRepository, times(1)).save(wishlistCaptor.capture());

        List<ProductEntity> capturedProducts = wishlistCaptor.getValue().getProducts();
        assertTrue(capturedProducts.contains(AddProductToEntityMapper.toEntity(addProductRequestOutput)));
    }

    @Test
    void shouldRemoveProductFromWishlist() {
        wishlistEntity = WishlistEntity.builder()
                .id("wishlistEntity-id")
                .clientId(clientId)
                .products(new ArrayList<>(List.of(AddProductToEntityMapper.toEntity(addProductRequestOutput))))
                .build();

        when(springDataRepository.findByClientId(clientId)).thenReturn(Optional.of(wishlistEntity));

        wishlistRepository.remove(clientId, addProductRequestOutput.getId());

        ArgumentCaptor<WishlistEntity> wishlistCaptor = ArgumentCaptor.forClass(WishlistEntity.class);
        verify(springDataRepository, times(1)).save(wishlistCaptor.capture());

        List<ProductEntity> capturedProducts = wishlistCaptor.getValue().getProducts();
        assertFalse(capturedProducts.contains(AddProductToEntityMapper.toEntity(addProductRequestOutput)));
    }

    @Test
    void shouldFindWishlistByClientId() {
        when(springDataRepository.findByClientId(clientId)).thenReturn(Optional.of(wishlistEntity));

        WishlistResponseOutput foundWishlistEntity = wishlistRepository.findOrCreate(clientId);

        assertNotNull(foundWishlistEntity);
        assertEquals(clientId, foundWishlistEntity.getClientId());
        assertEquals(1, foundWishlistEntity.getProducts().size());
        assertEquals("1", foundWishlistEntity.getProducts().get(0).getId());
    }

    @Test
    void shouldThrowExceptionWhenWishlistLimitExceeded() {
        List<ProductEntity> products= new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            products.add(ProductEntity.builder()
                    .id(String.valueOf(i))
                    .name("ProductEntity " + i)
                    .description("Description " + i)
                    .build());
        }

        wishlistEntity = WishlistEntity.builder()
                .id("wishlistEntity-id")
                .clientId(clientId)
                .products(products)
                .build();

        when(springDataRepository.findByClientId(clientId)).thenReturn(Optional.of(wishlistEntity));

        AddProductRequestOutput productEntityToAdd = AddProductRequestOutput.builder()
                .id("21")
                .name("New ProductEntity")
                .description("New ProductEntity Description")
                .build();

        assertThrows(WishlistLimitExceededException.class, () -> wishlistRepository.save(clientId, productEntityToAdd));
    }

    @Test
    void shouldThrowExceptionWhenProductAlreadyInWishlist() {
        wishlistEntity = WishlistEntity.builder()
                .id("wishlistEntity-id")
                .clientId(clientId)
                .products(new ArrayList<>(List.of(AddProductToEntityMapper.toEntity(addProductRequestOutput))))
                .build();

        when(springDataRepository.findByClientId(clientId)).thenReturn(Optional.of(wishlistEntity));

        AddProductRequestOutput productEntityToAdd = AddProductRequestOutput.builder()
                .id("1")
                .name("Garrafa")
                .description("Garrafa de café")
                .build();

        assertThrows(ProductAlreadyInWishlistException.class, () -> wishlistRepository.save(clientId, productEntityToAdd));
    }

    @Test
    void shouldThrowExceptionWhenProductNotFoundInWishlist() {
        when(springDataRepository.findByClientId(clientId)).thenReturn(Optional.of(wishlistEntity));
        assertThrows(ProductNotFoundException.class, () -> wishlistRepository.checkProductInWishlist(clientId, "non-existing-id"));
    }

    @Test
    void shouldCheckProductInWishlistSuccessfully() {
        when(springDataRepository.findByClientId(clientId)).thenReturn(Optional.of(wishlistEntity));
        assertDoesNotThrow(() -> wishlistRepository.checkProductInWishlist(clientId, addProductRequestOutput.getId()));
    }



}
