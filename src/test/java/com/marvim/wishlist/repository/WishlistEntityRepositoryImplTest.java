package com.marvim.wishlist.repository;

import com.marvim.wishlist.exception.global.ProductAlreadyInWishlistException;
import com.marvim.wishlist.exception.global.ProductNotFoundException;
import com.marvim.wishlist.exception.global.WishlistLimitExceededException;
import com.marvim.wishlist.output.dto.request.AddProductRequestOutput;
import com.marvim.wishlist.output.dto.response.WishlistResponseOutput;
import com.marvim.wishlist.repository.entity.ProductEntity;
import com.marvim.wishlist.repository.entity.WishlistEntity;
import com.marvim.wishlist.repository.mapper.RepositoryMappers;
import com.marvim.wishlist.repository.mongo.SpringDataWishlistRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Value;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class WishlistEntityRepositoryImplTest {

    @Mock
    private SpringDataWishlistRepository springDataRepository;

    @InjectMocks
    private WishlistRepositoryImpl wishlistRepository;

    private WishlistEntity wishlistEntity;
    private AddProductRequestOutput addProductRequestOutput;
    private final String clientId = "client-id";

    @Value("${wishlist.max.product.limit}")

    @BeforeEach
    void setUp() {
        addProductRequestOutput = new AddProductRequestOutput("1", "name", "description");

        wishlistEntity = new WishlistEntity("wishlistEntity-id", clientId, List.of(RepositoryMappers.toProduct(addProductRequestOutput)));
    }

    @Test
    void shouldAddProductToWishlist() {
        wishlistEntity = new WishlistEntity("wishlistEntity-id", clientId, new ArrayList<>());

        when(springDataRepository.findByClientId(clientId)).thenReturn(Optional.of(wishlistEntity));
        when(springDataRepository.save(wishlistEntity)).thenReturn(wishlistEntity);

        wishlistRepository.save(clientId, addProductRequestOutput);

        ArgumentCaptor<WishlistEntity> wishlistCaptor = ArgumentCaptor.forClass(WishlistEntity.class);
        verify(springDataRepository, times(1)).save(wishlistCaptor.capture());

        List<ProductEntity> capturedProducts = wishlistCaptor.getValue().products();
        assertTrue(capturedProducts.contains(RepositoryMappers.toProduct(addProductRequestOutput)));
    }

    @Test
    void shouldRemoveProductFromWishlist() {
        wishlistEntity = new WishlistEntity("wishlistEntity-id", clientId, List.of(RepositoryMappers.toProduct(addProductRequestOutput)));

        when(springDataRepository.findByClientId(clientId)).thenReturn(Optional.of(wishlistEntity));

        wishlistRepository.remove(clientId, addProductRequestOutput.id());

        ArgumentCaptor<WishlistEntity> wishlistCaptor = ArgumentCaptor.forClass(WishlistEntity.class);
        verify(springDataRepository, times(1)).save(wishlistCaptor.capture());

        List<ProductEntity> capturedProducts = wishlistCaptor.getValue().products();
        assertFalse(capturedProducts.contains(RepositoryMappers.toProduct(addProductRequestOutput)));
    }

    @Test
    void shouldFindWishlistByClientId() {
        when(springDataRepository.findByClientId(clientId)).thenReturn(Optional.of(wishlistEntity));

        WishlistResponseOutput foundWishlistEntity = wishlistRepository.findOrCreate(clientId);

        assertNotNull(foundWishlistEntity);
        assertEquals(clientId, foundWishlistEntity.clientId());
        assertEquals(1, foundWishlistEntity.products().size());
        assertEquals("1", foundWishlistEntity.products().get(0).id());
    }

    @Test
    void shouldThrowExceptionWhenWishlistLimitExceeded() {
        List<ProductEntity> products = java.util.stream.IntStream.range(0, 20)
                .mapToObj(i -> new ProductEntity(
                        String.valueOf(i),
                        "ProductEntity " + i,
                        "Description " + i))
                .toList();


        wishlistEntity = new WishlistEntity("wishlistEntity-id", clientId, products);

        when(springDataRepository.findByClientId(clientId)).thenReturn(Optional.of(wishlistEntity));

        AddProductRequestOutput productEntityToAdd = new AddProductRequestOutput(
                "21",
                "New ProductEntity",
                "New ProductEntity Description");

        assertThrows(WishlistLimitExceededException.class, () -> wishlistRepository.save(clientId, productEntityToAdd));
    }

    @Test
    void shouldThrowExceptionWhenProductAlreadyInWishlist() {
        wishlistEntity = new WishlistEntity("wishlistEntity-id", clientId, List.of(RepositoryMappers.toProduct(addProductRequestOutput)));

        when(springDataRepository.findByClientId(clientId)).thenReturn(Optional.of(wishlistEntity));

        AddProductRequestOutput productEntityToAdd = new AddProductRequestOutput("1", "name", "description");

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
        assertDoesNotThrow(() -> wishlistRepository.checkProductInWishlist(clientId, addProductRequestOutput.id()));
    }


}
