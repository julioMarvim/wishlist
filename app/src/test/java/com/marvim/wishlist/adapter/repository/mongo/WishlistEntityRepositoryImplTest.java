package com.marvim.wishlist.adapter.repository.mongo;

import com.marvim.wishlist.output.dto.response.WishlistResponseOutputDto;
import com.marvim.wishlist.repository.entity.ProductEntity;
import com.marvim.wishlist.repository.entity.WishlistEntity;
import com.marvim.wishlist.repository.mapper.AddProductMapper;
import com.marvim.wishlist.input.exception.ProductAlreadyInWishlistException;
import com.marvim.wishlist.input.exception.ProductNotFoundException;
import com.marvim.wishlist.input.exception.WishlistLimitExceededException;
import com.marvim.wishlist.input.exception.WishlistNotFoundException;
import com.marvim.wishlist.output.dto.request.AddProductRequestOutputDto;
import com.marvim.wishlist.repository.mongo.SpringDataWishlistRepository;
import com.marvim.wishlist.repository.mongo.WishlistRepositoryImpl;
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
    private AddProductRequestOutputDto addProductRequestOutputDto;
    private final String clientId = "client-id";

    @BeforeEach
    void setUp() {
        addProductRequestOutputDto = AddProductRequestOutputDto.builder()
                .id("1")
                .name("Garrafa")
                .description("Garrafa de café")
                .build();

        wishlistEntity = WishlistEntity.builder()
                .id("wishlistEntity-id")
                .clientId(clientId)
                .products(List.of(AddProductMapper.toEntity(addProductRequestOutputDto)))
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

        wishlistRepository.save(clientId, addProductRequestOutputDto);

        ArgumentCaptor<WishlistEntity> wishlistCaptor = ArgumentCaptor.forClass(WishlistEntity.class);
        verify(springDataRepository, times(1)).save(wishlistCaptor.capture());

        List<ProductEntity> capturedProductyEntities = wishlistCaptor.getValue().getProducts();
        assertTrue(capturedProductyEntities.contains(AddProductMapper.toEntity(addProductRequestOutputDto)));
    }

    @Test
    void shouldRemoveProductFromWishlist() {
        wishlistEntity = WishlistEntity.builder()
                .id("wishlistEntity-id")
                .clientId(clientId)
                .products(new ArrayList<>(List.of(AddProductMapper.toEntity(addProductRequestOutputDto))))
                .build();

        when(springDataRepository.findByClientId(clientId)).thenReturn(Optional.of(wishlistEntity));

        wishlistRepository.remove(clientId, addProductRequestOutputDto.getId());

        ArgumentCaptor<WishlistEntity> wishlistCaptor = ArgumentCaptor.forClass(WishlistEntity.class);
        verify(springDataRepository, times(1)).save(wishlistCaptor.capture());

        List<ProductEntity> capturedProductyEntities = wishlistCaptor.getValue().getProducts();
        assertFalse(capturedProductyEntities.contains(AddProductMapper.toEntity(addProductRequestOutputDto)));
    }

    @Test
    void shouldFindWishlistByClientId() {
        when(springDataRepository.findByClientId(clientId)).thenReturn(Optional.of(wishlistEntity));

        WishlistResponseOutputDto foundWishlistEntity = wishlistRepository.findByClientId(clientId);

        assertNotNull(foundWishlistEntity);
        assertEquals(clientId, foundWishlistEntity.getClientId());
        assertEquals(1, foundWishlistEntity.getProducts().size());
        assertEquals("1", foundWishlistEntity.getProducts().get(0).getId());
    }

    @Test
    void shouldThrowExceptionWhenWishlistLimitExceeded() {
        List<ProductEntity> productyEntities = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            productyEntities.add(ProductEntity.builder()
                    .id(String.valueOf(i))
                    .name("ProductEntity " + i)
                    .description("Description " + i)
                    .build());
        }

        wishlistEntity = WishlistEntity.builder()
                .id("wishlistEntity-id")
                .clientId(clientId)
                .products(productyEntities)
                .build();

        when(springDataRepository.findByClientId(clientId)).thenReturn(Optional.of(wishlistEntity));

        AddProductRequestOutputDto productEntityToAdd = AddProductRequestOutputDto.builder()
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
                .products(new ArrayList<>(List.of(AddProductMapper.toEntity(addProductRequestOutputDto))))
                .build();

        when(springDataRepository.findByClientId(clientId)).thenReturn(Optional.of(wishlistEntity));

        AddProductRequestOutputDto productEntityToAdd = AddProductRequestOutputDto.builder()
                .id("1")
                .name("Garrafa")
                .description("Garrafa de café")
                .build();

        assertThrows(ProductAlreadyInWishlistException.class, () -> wishlistRepository.save(clientId, productEntityToAdd));
    }

    @Test
    void shouldThrowExceptionWhenWishlistNotFound() {
        when(springDataRepository.findByClientId(clientId)).thenReturn(Optional.empty());
        assertThrows(WishlistNotFoundException.class, () -> wishlistRepository.checkProductInWishlist(clientId, addProductRequestOutputDto.getId()));
    }

    @Test
    void shouldThrowExceptionWhenProductNotFoundInWishlist() {
        when(springDataRepository.findByClientId(clientId)).thenReturn(Optional.of(wishlistEntity));
        assertThrows(ProductNotFoundException.class, () -> wishlistRepository.checkProductInWishlist(clientId, "non-existing-id"));
    }

    @Test
    void shouldCheckProductInWishlistSuccessfully() {
        when(springDataRepository.findByClientId(clientId)).thenReturn(Optional.of(wishlistEntity));
        assertDoesNotThrow(() -> wishlistRepository.checkProductInWishlist(clientId, addProductRequestOutputDto.getId()));
    }



}
